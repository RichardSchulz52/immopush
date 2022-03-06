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
import java.util.stream.Collectors;

@Service
public class Searcher {

    private final UrlRepository urlRepository;
    private final SearchRepository searchRepository;

    public Searcher(UrlRepository urlRepository, SearchRepository searchRepository) {
        this.urlRepository = urlRepository;
        this.searchRepository = searchRepository;
    }

    public Map<String, List<DetailedOffer>> searchNew() {
        List<SearchRequest> allSearchRequests = searchRepository.findAll();
        Map<String, List<SearchRequest>> chatIdToRequests = allSearchRequests.stream()
                .map(SearchRequest::getChatId).distinct().collect(Collectors.toMap(cId -> cId, cId -> allSearchRequests.stream().filter(sr -> sr.getChatId().equals(cId)).collect(Collectors.toList())));

        Map<String, List<DetailedOffer>> chatIdToFoundUrls = new HashMap<>();
        chatIdToRequests.forEach((chatId, searchRequests) -> {
            Map<SearchRequest, List<URL>> foundUrls = new HashMap<>();
            for (SearchRequest sr : searchRequests) {
                List<URL> strip = findOn(sr.getUrl());
                List<URL> forChatIds = foundUrls.computeIfAbsent(sr, str -> new ArrayList<>());
                forChatIds.addAll(strip);
            }
            NewsFilter newsFilter = new NewsFilter(urlRepository);
            for (SearchRequest sr : foundUrls.keySet()) {
                List<URL> unfiltered = foundUrls.get(sr);
                List<URL> news = newsFilter.filter(unfiltered, sr.getChatId());
                urlRepository.saveAll(FoundUrl.from(news, sr.getChatId()));
                List<DetailedOffer> detailedOffers = addDetails(sr.getSearchName(), news);
                chatIdToFoundUrls.computeIfAbsent(chatId, c -> new ArrayList<>()).addAll(detailedOffers);
            }
        });

        return chatIdToFoundUrls;
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

    private List<DetailedOffer> addDetails(String searchName, List<URL> found) {
        List<DetailedOffer> detailedOffers = new ArrayList<>();
        for (URL url : found) {
            String offerHtml = WebFetcher.fetch(url);
            WebsiteParser parser = SupportedHosts.getParser(url.getHost());
            DetailedOffer detailedOffer = parser.details(searchName, url, offerHtml);
            detailedOffers.add(detailedOffer);
        }
        return detailedOffers;
    }

}
