package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.CommandParamsParser;

public class DeleteUserCommand extends ChatCommand<DeleteUserParams> {
    @Override
    public String commandIdentifier() {
        return "deleteUser";
    }

    @Override
    public String expectedParams() {
        return "userId";
    }

    @Override
    public String description() {
        return "Revokes the rights to command this bot for user with the given id.  You must be the owner of the bot to use this command.";
    }

    @Override
    public DeleteUserParams mapToParams(Message message, String paramsString) {
        CommandParamsParser<DeleteUserCommand> paramParser = new CommandParamsParser<>(paramsString, this);
        return new DeleteUserParams(message, paramParser.nextLong());
    }
}
