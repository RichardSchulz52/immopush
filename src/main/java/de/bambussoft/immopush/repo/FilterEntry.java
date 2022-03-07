package de.bambussoft.immopush.repo;

import de.bambussoft.immopush.fetch.filter.FilterAttribute;
import de.bambussoft.immopush.fetch.filter.FilterRelation;

import javax.persistence.*;

@Entity
public class FilterEntry {

    @Id
    @GeneratedValue
    private long id;
    private String chatId;
    private String searchName;
    @Enumerated(EnumType.STRING)
    private FilterAttribute attribute;
    @Enumerated(EnumType.STRING)
    private FilterRelation filterRelation;
    private Integer value;

    public FilterEntry() {
    }

    public FilterEntry(String chatId, String searchName, FilterAttribute attribute, FilterRelation filterRelation, Integer value) {
        this.chatId = chatId;
        this.searchName = searchName;
        this.attribute = attribute;
        this.filterRelation = filterRelation;
        this.value = value;
    }

    public String getChatId() {
        return chatId;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
