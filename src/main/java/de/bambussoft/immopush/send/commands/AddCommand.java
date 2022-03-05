package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.CommandParamsParser;

public class AddCommand extends ChatCommand<AddParams> {

    @Override
    public String commandIdentifier() {
        return "add";
    }

    @Override
    public String expectedParams() {
        return "name url";
    }

    @Override
    public String description() {
        return "The name is used to identify the search request. The name must be unique.";
    }

    @Override
    public AddParams mapToParams(Message message, String paramsString) {
        CommandParamsParser<AddCommand> paramParser = new CommandParamsParser<>(paramsString, this);
        return new AddParams(message, paramParser.nextStr(), paramParser.nextStr());
    }
}
