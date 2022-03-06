package de.bambussoft.immopush.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SearchRequest {

    @Id
    @GeneratedValue
    long id;
    @Column(nullable = false, length = 2000)
    String url;
    @Column(nullable = false)
    String chatId;
    @Column
    String searchName;

    public SearchRequest() {
    }

    public SearchRequest(String url, String chatId, String searchName) {
        this.url = url;
        this.chatId = chatId;
        this.searchName = searchName;
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

    public String getSearchName() {
        return searchName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRequest that = (SearchRequest) o;
        return id == that.id && Objects.equals(url, that.url) && Objects.equals(chatId, that.chatId) && Objects.equals(searchName, that.searchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, chatId, searchName);
    }
}
