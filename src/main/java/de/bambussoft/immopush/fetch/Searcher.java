package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.SupportedHosts;
import de.bambussoft.immopush.repo.FoundUrl;
import de.bambussoft.immopush.repo.SearchRepository;
import de.bambussoft.immopush.repo.SearchRequest;
import de.bambussoft.immopush.repo.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
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
        for (String urlString : urls) {
            try {
                URL url = new URL(urlString);
                String website = WebFetcher.fetch(url);
                WebsiteParser parser = SupportedHosts.getParser(url.getHost());
                List<URL> strip = parser.parse(website);
                foundUrls.addAll(strip);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        NewsFilter newsFilter = new NewsFilter(urlRepository);
        List<URL> news = newsFilter.filter(foundUrls);
        urlRepository.saveAll(FoundUrl.from(news));
        return news;
    }

}
