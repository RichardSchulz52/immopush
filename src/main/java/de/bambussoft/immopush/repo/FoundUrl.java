package de.bambussoft.immopush.repo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class FoundUrl {

    @Id
    private String url;

    public FoundUrl() {
    }

    public FoundUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static List<FoundUrl> from(List<URL> urls) {
        return urls.stream().map(url -> new FoundUrl(url.toString())).collect(Collectors.toList());
    }


}
