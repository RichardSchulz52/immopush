package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.Searcher;
import de.bambussoft.immopush.send.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URL;
import java.util.List;

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
		List<URL> newUrls = searcher.searchNew();
		newUrls.forEach(url -> bot.send(url.toString()));
		//newUrls.forEach(url -> System.out.println(url.toString()));
	}
}
