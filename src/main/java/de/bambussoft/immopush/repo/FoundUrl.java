package de.bambussoft.immopush.repo;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(indexes = {@Index(name = "I_URL", columnList = "URL", unique = true),
        @Index(name = "I_CHAT_ID", columnList = "CHAT_ID")})
public class FoundUrl {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "URL", nullable = false)
    private String url;
    @Column(name = "CHAT_ID", nullable = false)
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
