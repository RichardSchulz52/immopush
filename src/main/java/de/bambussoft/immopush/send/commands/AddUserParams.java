package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.HasMessage;

public class AddUserParams extends HasMessage {
    private final Long userId;
    private final String name;

    public AddUserParams(Message message, Long userId, String name) {
        super(message);
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
