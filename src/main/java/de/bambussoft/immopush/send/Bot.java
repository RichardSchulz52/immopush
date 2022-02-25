package de.bambussoft.immopush.send;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import de.bambussoft.immopush.SearchConfiguration;
import de.bambussoft.immopush.SupportedHosts;
import de.bambussoft.immopush.repo.AllowedUsers;
import de.bambussoft.immopush.repo.AllowedUsersRepository;
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

import static de.bambussoft.immopush.send.BotCommands.*;

@Service
public class Bot {

    @Value("${bot.token}")
    private String token;
    @Value("${bot.owner.chatId}")
    private String ownerChatId;
    @Value("${bot.owner}")
    private String owner;

    private TelegramBot telegramBot;

    private final TreeSet<Long> allowedUsers = new TreeSet<>();
    private final SearchConfiguration searchConfiguration;
    private final AllowedUsersRepository allowedUsersRepository;

    @Autowired
    public Bot(SearchConfiguration searchConfiguration, AllowedUsersRepository allowedUsersRepository) {
        this.searchConfiguration = searchConfiguration;
        this.allowedUsersRepository = allowedUsersRepository;
    }

    @PostConstruct
    void setup() {
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(this::processUpdates);
        allowedUsers.addAll(allowedUsersRepository.findAll().stream().map(AllowedUsers::getTelegramId).collect(Collectors.toList()));
    }

    public void send(String chatId, String message) {
        telegramBot.execute(new SendMessage(chatId, message));
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
                    processCommands(chatId, message);
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

    private void processCommands(String chatId, Message message) {
        if (message.text().contains(ADD_SEARCH_REQUEST)) {
            processSearchRequest(message);
        } else if (message.text().contains(DISPLAY_SEARCH_REQUEST)) {
            processDisplayRequest(chatId);
        } else if (message.text().contains(DELETE_SEARCH_REQUEST)) {
            processDeleteRequest(message);
        } else if (isOwner(message)) {
            if (message.text().contains(DELETE_ALLOWED_USER)) {
                processDeleteUser(chatId, message);
            } else if (message.text().contains(ADD_ALLOWED_USER)) {
                processAddUser(chatId, message);
            }
        }
    }

    private void processDeleteUser(String chatId, Message message) {
        String longStr = message.text().substring(DELETE_ALLOWED_USER.length()).trim();
        try {
            long userId = Long.parseLong(longStr);
            allowedUsersRepository.deleteById(userId);
            allowedUsers.remove(userId);
            send(chatId, "Deleted!");
        } catch (NumberFormatException e) {
            send(chatId, "Not a number");
        } catch (EmptyResultDataAccessException e) {
            send(chatId, "Nothing to delete");
        }
    }

    private void processAddUser(String chatId, Message message) {
        String longStr = message.text().substring(ADD_ALLOWED_USER.length()).trim();
        try {
            long userId = Long.parseLong(longStr);
            // TODO set name with command param
            allowedUsersRepository.save(new AllowedUsers(userId, ""));
            allowedUsers.add(userId);
            send(chatId, "Added!");
        } catch (NumberFormatException e) {
            send(chatId, "Not a number");
        }
    }

    private void informUnauthorisedRequest(String chatId, Message message) {
        send(chatId, "You are not allowed to send commands");
        String unauthorizedUser = ApiExtractor.userNames(message);
        send(ownerChatId, "Unauthorised user " + unauthorizedUser + " id: " + message.from().id() +
                " send: " + message.text());
    }

    private void processDisplayRequest(String chatId) {
        String configs = searchConfiguration.allToPrint(chatId);
        if (configs.isBlank()) {
            configs = "No entries";
        }
        send(chatId, configs);
    }

    private void processSearchRequest(Message message) {
        String chatId = message.chat().id().toString();
        String url = message.text().substring(ADD_SEARCH_REQUEST.length()).trim();
        try {
            URL isUrl = new URL(url);
            if (SupportedHosts.isSupported(isUrl.getHost())) {
                searchConfiguration.addSearch(url, chatId);
                send(chatId, "added!");
            } else {
                send(chatId, "Not a supported website");
            }
        } catch (MalformedURLException e) {
            send(chatId, "This is not a valid URL");
        }
    }

    private void processDeleteRequest(Message message) {
        String chatId = message.chat().id().toString();
        String longStr = message.text().substring(DELETE_SEARCH_REQUEST.length()).trim();
        try {
            long id = Long.parseLong(longStr);
            searchConfiguration.delete(id);
            boolean worked = searchConfiguration.delete(id);
            if (worked) {
                send(chatId, "Deleted!");
            } else {
                send(chatId, "Nothing to delete");
            }
        } catch (NumberFormatException e) {
            send(chatId, "Not a number");
        }
    }
}
