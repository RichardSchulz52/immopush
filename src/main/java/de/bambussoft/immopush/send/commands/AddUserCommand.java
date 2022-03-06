package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.CommandParamsParser;

public class AddUserCommand extends ChatCommand<AddUserParams> {
    @Override
    public String commandIdentifier() {
        return "addUser";
    }

    @Override
    public String expectedParams() {
        return "userId name";
    }

    @Override
    public String description() {
        return "allows the user to command the bot. You must be the owner of the bot to use this command.";
    }

    @Override
    public AddUserParams mapToParams(Message message, String paramsString) {
        CommandParamsParser<AddUserCommand> paramParser = new CommandParamsParser<>(paramsString, this);
        return new AddUserParams(message, paramParser.nextLong(), paramParser.nextStr());
    }
}
