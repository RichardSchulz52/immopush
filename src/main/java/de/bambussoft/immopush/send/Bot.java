package de.bambussoft.immopush.send;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import de.bambussoft.immopush.SearchConfiguration;
import de.bambussoft.immopush.SupportedHosts;
import de.bambussoft.immopush.repo.*;
import de.bambussoft.immopush.send.commands.*;
import de.bambussoft.immopush.send.commands.util.CommandException;
import de.bambussoft.immopush.send.commands.util.CommandsManager;
import de.bambussoft.immopush.send.commands.util.OnlyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class Bot {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TreeSet<Long> allowedUsers = new TreeSet<>();
    private final SearchConfiguration searchConfiguration;
    private final AllowedUsersRepository allowedUsersRepository;
    private final FailedMessageRepository failedMessageRepository;
    private final FilterEntryRepository filterEntryRepository;
    private SearchRepository searchRepository;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.owner.chatId}")
    private String ownerChatId;
    @Value("${bot.owner}")
    private String owner;
    private TelegramBot telegramBot;
    private CommandsManager commandsManager;

    @Autowired
    public Bot(SearchConfiguration searchConfiguration, AllowedUsersRepository allowedUsersRepository, FailedMessageRepository failedMessageRepository, FilterEntryRepository filterEntryRepository, SearchRepository searchRepository) {
        this.searchConfiguration = searchConfiguration;
        this.allowedUsersRepository = allowedUsersRepository;
        this.failedMessageRepository = failedMessageRepository;
        this.filterEntryRepository = filterEntryRepository;
        this.searchRepository = searchRepository;
    }

    @PostConstruct
    void setup() {
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(this::processUpdates);
        allowedUsers.addAll(allowedUsersRepository.findAll().stream().map(AllowedUsers::getTelegramId).collect(Collectors.toList()));
        commandsManager = new CommandsManager();
        commandsManager.registerCommand(new AddCommand(), this::processSearchRequest);
        commandsManager.registerCommand(new DisplayCommand(), this::processDisplayRequests);
        commandsManager.registerCommand(new DeleteSrCommand(), this::processDeleteRequest);
        commandsManager.registerCommand(new FilterCommand(), this::processFilter);
        commandsManager.registerCommand(new AddUserCommand(), this::processAddUser);
        commandsManager.registerCommand(new DeleteUserCommand(), this::processDeleteUser);
        commandsManager.registerCommand(new DisplayUsersCommand(), this::processDisplayUsers);
    }

    public void answer(Message incoming, String answer) {
        send(incoming.chat().id().toString(), answer);
    }

    public void send(String chatId, String message) {
        boolean failed = sendRaw(chatId, message);
        if (failed) {
            FailedMessage failedMessage = new FailedMessage(chatId, message);
            logger.info("failed to send: " + failedMessage);
            failedMessageRepository.save(failedMessage);
        }
    }

    public boolean resend(String chatId, String message) {
        boolean success = !sendRaw(chatId, message);
        return success;
    }

    private boolean sendRaw(String chatId, String message) {
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message));
        String trimmed = message.trim();
        if (trimmed.endsWith("\n")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return response.message() == null || response.message().text() == null || !response.message().text().equals(trimmed);
    }

    private int processUpdates(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() == null || update.message().chat() == null || update.message().chat().id() == null) {
                return;
            }
            String chatId = update.message().chat().id().toString();
            Message message = update.message();
            if (isCommand(message)) {
                if (!userIsAllowed(message)) {
                    informUnauthorisedRequest(chatId, message);
                } else {
                    processCommands(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean isCommand(Message message) {
        return message != null && message.viaBot() == null &&
                message.text() != null && message.text().startsWith("/") &&
                message.from() != null;
    }

    private boolean userIsAllowed(Message message) {
        return isOwner(message) || allowedUsers.contains(message.from().id());
    }

    private boolean isOwner(Message message) {
        return Objects.equals(owner, message.from().username());
    }

    private void processCommands(Message message) {
        try {
            commandsManager.callFor(message);
        } catch (CommandException ce) {
            answer(message, ce.getMessage());
        }
    }

    private void processDeleteUser(DeleteUserParams params) {
        try {
            long userId = params.getUserId();
            allowedUsersRepository.deleteById(userId);
            allowedUsers.remove(userId);
            answer(params.getMessage(), "Deleted!");
        } catch (EmptyResultDataAccessException e) {
            answer(params.getMessage(), "Nothing to delete");
        }
    }

    private void processAddUser(AddUserParams addUserParams) {
        long userId = addUserParams.getUserId();
        String name = addUserParams.getName();
        allowedUsersRepository.save(new AllowedUsers(userId, name));
        allowedUsers.add(userId);
        answer(addUserParams.getMessage(), "Added!");
    }

    private void informUnauthorisedRequest(String chatId, Message message) {
        send(chatId, "You are not allowed to send commands");
        String unauthorizedUser = ApiExtractor.userNames(message);
        send(ownerChatId, "Unauthorised user " + unauthorizedUser + " id: " + message.from().id() +
                " send: " + message.text());
    }

    private void processDisplayRequests(OnlyMessage onlyMessage) {
        String chatId = onlyMessage.getChatId();
        List<SearchRequest> searchRequests = searchRepository.findAllByChatId(chatId);
        List<FilterEntry> filters = filterEntryRepository.findByChatId(chatId);
        String details = PrintFormatter.details(searchRequests, filters);
        answer(onlyMessage.getMessage(), details);
    }

    private void processFilter(FilterParams filterParams) {
        String searchName = filterParams.getSearchName();
        String chatId = filterParams.getChatId();
        SearchRequest searchRequest = searchConfiguration.find(chatId, searchName);
        if (searchRequest == null) {
            answer(filterParams.getMessage(), "no search with name " + searchName);
        } else {
            FilterEntry filterEntry = new FilterEntry(chatId, searchName, filterParams.getAttribute(), filterParams.getFilterRelation(), filterParams.getValue());
            List<FilterEntry> existing = filterEntryRepository.findByChatIdAndSearchNameAndAttribute(chatId, searchName, filterParams.getAttribute());
            if (!existing.isEmpty()) {
                FilterEntry oldFilter = existing.get(0);
                filterEntry.setId(oldFilter.getId());
                answer(filterParams.getMessage(), "updated!");
            } else {
                answer(filterParams.getMessage(), "added!");
            }
            filterEntryRepository.save(filterEntry);
        }
    }

    private void processSearchRequest(AddParams addParams) {
        Message message = addParams.getMessage();
        String chatId = message.chat().id().toString();
        try {
            String url = addParams.getUrl();
            String searchName = addParams.getName();
            URL isUrl = new URL(url);
            if (SupportedHosts.isSupported(isUrl.getHost())) {
                searchConfiguration.addSearch(url, chatId, searchName);
                answer(message, "added!");
            } else {
                answer(message, "Not a supported website");
            }
        } catch (MalformedURLException e) {
            answer(message, "This is not a valid URL");
        }
    }

    private void processDeleteRequest(DeleteSrParams params) {
        String name = params.getName();
        boolean worked = searchConfiguration.delete(name, params.getChatId());
        if (worked) {
            answer(params.getMessage(), "Deleted!");
        } else {
            answer(params.getMessage(), "Nothing to delete");
        }

    }

    private void processDisplayUsers(OnlyMessage onlyMessage) {
        String usersList = "Authorized users:\n";
        usersList += allowedUsersRepository.findAll().stream()
                .map(u -> u.getTelegramId() + " " + u.getNames())
                .collect(Collectors.joining("\n"));
        answer(onlyMessage.getMessage(), usersList);
    }
}
