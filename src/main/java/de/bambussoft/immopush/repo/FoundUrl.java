package de.bambussoft.immopush.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class FoundUrl {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String chatId;

    public FoundUrl() {
    }

    public FoundUrl(String url, String chatId) {
        this.url = url;
        this.chatId = chatId;
    }

    public String getUrl() {
        return url;
    }

    public String getChatId() {
        return chatId;
    }

    public static List<FoundUrl> from(List<URL> urls, String chatId) {
        return urls.stream().map(url -> new FoundUrl(url.toString(), chatId)).collect(Collectors.toList());
    }


}
