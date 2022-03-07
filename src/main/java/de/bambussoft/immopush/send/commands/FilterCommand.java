package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.fetch.filter.FilterAttribute;
import de.bambussoft.immopush.fetch.filter.FilterRelation;
import de.bambussoft.immopush.send.commands.util.ChatCommand;
import de.bambussoft.immopush.send.commands.util.CommandParamsParser;

public class FilterCommand extends ChatCommand<FilterParams> {
    @Override
    public String commandIdentifier() {
        return "filter";
    }

    @Override
    public String expectedParams() {
        return "searchName attribute relation value";
    }

    @Override
    public String description() {
        return "Filters the search results for the search with the searchName.\n" +
                "Attributes are: lotSize\n";
    }

    @Override
    public FilterParams mapToParams(Message message, String paramsString) {
        CommandParamsParser<FilterCommand> paramParser = new CommandParamsParser<>(paramsString, this);
        return new FilterParams(message,
                paramParser.nextStr(),
                paramParser.nextEnum(FilterAttribute.class),
                paramParser.nextEnum(FilterRelation.class),
                paramParser.nextInt());
    }
}
