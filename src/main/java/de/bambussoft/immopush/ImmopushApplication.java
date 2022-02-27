package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.Searcher;
import de.bambussoft.immopush.repo.FailedMessageRepository;
import de.bambussoft.immopush.send.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URL;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ImmopushApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Searcher searcher;
    private final Bot bot;
    private final FailedMessageRepository failedMessageRepository;

    public static void main(String[] args) {
        SpringApplication.run(ImmopushApplication.class, args);
    }

    @Autowired
    public ImmopushApplication(Searcher searcher, Bot bot, FailedMessageRepository failedMessageRepository) {
        this.searcher = searcher;
        this.bot = bot;
        this.failedMessageRepository = failedMessageRepository;
    }

    @Scheduled(fixedDelay = 30 * 1000)
    void eachMinute() {
        Map<String, List<URL>> chatIdToNews = searcher.searchNew();
        sendNews(chatIdToNews);
        resendFailed();
    }

    private void sendNews(Map<String, List<URL>> chatIdToNews) {
        chatIdToNews.forEach((key, value) -> value.forEach(url -> bot.send(key, url.toString())));
    }

    private void resendFailed() {
        failedMessageRepository.findAll().forEach(f -> {
            boolean success = bot.resend(f.getChatId(), f.getMessage());
            if (success) {
                logger.info("successfully resend: " + f);
                failedMessageRepository.delete(f);
            }
        });
    }
}
