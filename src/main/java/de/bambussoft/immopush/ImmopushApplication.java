package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.Searcher;
import de.bambussoft.immopush.send.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URL;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ImmopushApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImmopushApplication.class, args);
    }

    @Autowired
    Searcher searcher;

    @Autowired
    Bot bot;

    @Scheduled(fixedDelay = 10 * 1000)
    void eachMinute() {
        Map<String, List<URL>> chatIdToNews = searcher.searchNew();
        chatIdToNews.forEach((key, value) -> value.forEach(url -> bot.send(key, url.toString())));
    }
}
