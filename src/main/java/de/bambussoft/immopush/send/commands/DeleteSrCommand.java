package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.CommandParamsParser;

public class DeleteSrCommand extends ChatCommand<DeleteSrParams> {

    @Override
    public String commandIdentifier() {
        return "delete";
    }

    @Override
    public String expectedParams() {
        return "id";
    }

    @Override
    public String description() {
        return "deletes the search request with the given id";
    }

    @Override
    public DeleteSrParams mapToParams(Message message, String paramsString) {
        CommandParamsParser<DeleteSrCommand> parser = new CommandParamsParser<>(paramsString, this);
        return new DeleteSrParams(message, parser.nextLong());
    }
}
