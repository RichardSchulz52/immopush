package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.HasMessage;

public class DeleteSrParams extends HasMessage {
    private final String name;

    public DeleteSrParams(Message message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
