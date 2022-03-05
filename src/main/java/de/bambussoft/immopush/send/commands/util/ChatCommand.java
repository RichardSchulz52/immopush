package de.bambussoft.immopush.send.commands.util;

import com.pengrad.telegrambot.model.Message;

public abstract class ChatCommand<P extends HasMessage> {

    public abstract String commandIdentifier();

    public abstract String expectedParams();

    public abstract String description();

    public abstract P mapToParams(Message message, String paramsString);

    @Override
    public int hashCode() {
        return commandIdentifier().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatCommand) {
            ChatCommand<?> that = (ChatCommand<?>) obj;
            return this.commandIdentifier().equals(that.commandIdentifier());
        }
        return false;
    }
}
