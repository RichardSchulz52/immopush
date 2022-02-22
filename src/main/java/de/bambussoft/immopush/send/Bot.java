package de.bambussoft.immopush.send;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import de.bambussoft.immopush.SearchConfiguration;
import de.bambussoft.immopush.SupportedHosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static de.bambussoft.immopush.send.BotCommands.*;

@Service
public class Bot {

    @Value("${bot.token}")
    private String token;
    @Value("${bot.chatId}")
    private String chatId;
    private TelegramBot telegramBot;

    private final SearchConfiguration searchConfiguration;

    @Autowired
    public Bot(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
    }

    @PostConstruct
    void setup() {
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(this::processUpdates);
    }

    public void send(String message) {
        telegramBot.execute(new SendMessage(chatId, message));
    }

    private int processUpdates(List<Update> updates) {
        updates.forEach(update -> {
            Message message = update.message();
            if (isCommand(message)) {
                if (message.text().contains(ADD_SEARCH_REQUEST)) {
                    processSearchRequest(message);
                } else if (message.text().contains(DISPLAY_SEARCH_REQUEST)) {
                    String configs = searchConfiguration.allToPrint();
                    send(configs);
                } else if (message.text().contains(DELETE_SEARCH_REQUEST)) {
                    processDeleteRequest(message.text());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean isCommand(Message message) {
        return message != null && message.viaBot() == null && message.text() != null && message.text().startsWith("/");
    }

    private void processSearchRequest(Message message) {
        String url = message.text().substring(ADD_SEARCH_REQUEST.length()).trim();
        try {
            URL isUrl = new URL(url);
            if (SupportedHosts.isSupported(isUrl.getHost())) {
                searchConfiguration.addSearch(url);
                send("added!");
            } else {
                send("Not a supported website");
            }
        } catch (MalformedURLException e) {
            send("This is not a valid URL");
        }
    }

    private void processDeleteRequest(String text) {
        String longStr = text.substring(DELETE_SEARCH_REQUEST.length()).trim();
        try {
            long id = Long.parseLong(longStr);
            boolean worked = searchConfiguration.delete(id);
            if (worked) {
                send("Deleted!");
            } else {
                send("Nothing to delete");
            }
        } catch (NumberFormatException e) {
            send("Not a number");
        }
    }
}
