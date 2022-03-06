package de.bambussoft.immopush.send.commands.util;

import com.pengrad.telegrambot.model.Message;

public abstract class HasMessage {
    private final Message message;

    public HasMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public String getChatId() {
        return message.chat().id().toString();
    }
}
