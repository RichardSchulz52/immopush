package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.SupportedHosts;
import de.bambussoft.immopush.fetch.parser.WebsiteParser;
import de.bambussoft.immopush.repo.FoundUrl;
import de.bambussoft.immopush.repo.SearchRepository;
import de.bambussoft.immopush.repo.SearchRequest;
import de.bambussoft.immopush.repo.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Searcher {

    private final UrlRepository urlRepository;
    private final SearchRepository searchRepository;

    public Searcher(UrlRepository urlRepository, SearchRepository searchRepository) {
        this.urlRepository = urlRepository;
        this.searchRepository = searchRepository;
    }

    public Map<String, List<URL>> searchNew() {
        List<SearchRequest> searchRequests = searchRepository.findAll();
        Map<String, List<URL>> foundUrls = new HashMap<>();
        for (SearchRequest sr : searchRequests) {
            List<URL> strip = findOn(sr.getUrl());
            List<URL> forChatIds = foundUrls.computeIfAbsent(sr.getChatId(), str -> new ArrayList<>());
            forChatIds.addAll(strip);
        }
        NewsFilter newsFilter = new NewsFilter(urlRepository);
        for (String chatId : foundUrls.keySet()) {
            List<URL> unfiltered = foundUrls.get(chatId);
            List<URL> news = newsFilter.filter(unfiltered, chatId);
            urlRepository.saveAll(FoundUrl.from(news, chatId));
            foundUrls.put(chatId, news);
        }
        return foundUrls;
    }

    public List<URL> findOn(String urlString) {
        List<URL> strip = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            String website = WebFetcher.fetch(url);
            WebsiteParser parser = SupportedHosts.getParser(url.getHost());
            strip = parser.parse(website);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return strip;
    }

}
