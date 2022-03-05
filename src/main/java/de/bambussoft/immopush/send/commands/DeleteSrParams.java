package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.HasMessage;

public class DeleteSrParams extends HasMessage {
    private final long id;

    public DeleteSrParams(Message message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
