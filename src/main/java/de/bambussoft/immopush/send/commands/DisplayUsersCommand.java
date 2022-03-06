package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.OnlyMessage;

public class DisplayUsersCommand extends ChatCommand<OnlyMessage> {

    @Override
    public String commandIdentifier() {
        return "displayUsers";
    }

    @Override
    public String expectedParams() {
        return null;
    }

    @Override
    public String description() {
        return "Shows all authorised users. You must be the owner of the bot to use this command.";
    }

    @Override
    public OnlyMessage mapToParams(Message message, String paramsString) {
        return new OnlyMessage(message);
    }
}
