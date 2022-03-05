package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.HasMessage;

public class AddParams extends HasMessage {
    private final String name;
    private final String url;

    public AddParams(Message message, String name, String url) {
        super(message);
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
