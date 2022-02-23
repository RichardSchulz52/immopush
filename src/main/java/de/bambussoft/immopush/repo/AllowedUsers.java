package de.bambussoft.immopush.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AllowedUsers {

    @Id
    private long telegramId;
    @Column(nullable = false)
    private String names;

    public AllowedUsers() {

    }

    public AllowedUsers(long telegramId, String names) {
        this.telegramId = telegramId;
        this.names = names;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public String getNames() {
        return names;
    }
}
