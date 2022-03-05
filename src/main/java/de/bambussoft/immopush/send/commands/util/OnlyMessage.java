package de.bambussoft.immopush.send.commands.util;

import com.pengrad.telegrambot.model.Message;

public class OnlyMessage extends HasMessage {

    public OnlyMessage(Message message) {
        super(message);
    }
}
