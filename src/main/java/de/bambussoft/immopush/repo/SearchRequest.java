package de.bambussoft.immopush.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SearchRequest {

    @Id
    @GeneratedValue
    long id;
    @Column(nullable = false, length = 2000)
    String url;
    @Column(nullable = false)
    String chatId;

    public SearchRequest() {
    }

    public SearchRequest(String url, String chatId) {
        this.url = url;
        this.chatId = chatId;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getChatId() {
        return chatId;
    }

}
