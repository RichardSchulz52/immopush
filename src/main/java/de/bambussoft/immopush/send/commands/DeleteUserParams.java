package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.HasMessage;

public class DeleteUserParams extends HasMessage {
    private final Long userId;

    public DeleteUserParams(Message message, Long userId) {
        super(message);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
