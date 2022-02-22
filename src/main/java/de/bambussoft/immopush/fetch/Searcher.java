package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.repo.FoundUrl;
import de.bambussoft.immopush.repo.SearchRepository;
import de.bambussoft.immopush.repo.SearchRequest;
import de.bambussoft.immopush.repo.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Searcher {

    private final UrlRepository urlRepository;
    private final SearchRepository searchRepository;

    public Searcher(UrlRepository urlRepository, SearchRepository searchRepository) {
        this.urlRepository = urlRepository;
        this.searchRepository = searchRepository;
    }

    public List<URL> searchNew() {
        List<String> urls = searchRepository.findAll().stream().map(SearchRequest::getUrl).collect(Collectors.toList());
        List<URL> foundUrls = new ArrayList<>();
        for (String url : urls) {
            String website = WebFetcher.fetch(url);
            List<URL> strip = ListStripper.strip(website);
            foundUrls.addAll(strip);
        }
        NewsFilter newsFilter = new NewsFilter(urlRepository);
        List<URL> news = newsFilter.filter(foundUrls);
        urlRepository.saveAll(FoundUrl.from(news));
        return news;
    }

}
