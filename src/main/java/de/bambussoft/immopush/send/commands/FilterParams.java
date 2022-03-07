package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.fetch.filter.FilterAttribute;
import de.bambussoft.immopush.fetch.filter.FilterRelation;
import de.bambussoft.immopush.send.commands.util.HasMessage;

public class FilterParams extends HasMessage {

    private final String searchName;
    private final FilterAttribute attribute;
    private final FilterRelation filterRelation;
    private final Integer value;

    public FilterParams(Message message, String searchName, FilterAttribute attribute, FilterRelation filterRelation, Integer value) {
        super(message);
        this.searchName = searchName;
        this.attribute = attribute;
        this.filterRelation = filterRelation;
        this.value = value;
    }

    public String getSearchName() {
        return searchName;
    }

    public FilterAttribute getAttribute() {
        return attribute;
    }

    public FilterRelation getFilterRelation() {
        return filterRelation;
    }

    public Integer getValue() {
        return value;
    }
}
