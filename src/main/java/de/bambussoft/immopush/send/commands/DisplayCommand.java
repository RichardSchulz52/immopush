package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.OnlyMessage;

public class DisplayCommand extends ChatCommand<OnlyMessage> {

    @Override
    public String commandIdentifier() {
        return "display";
    }

    @Override
    public String expectedParams() {
        return null;
    }

    @Override
    public String description() {
        return "shows the current search request";
    }

    @Override
    public OnlyMessage mapToParams(Message message, String paramsString) {
        return new OnlyMessage(message);
    }
}
