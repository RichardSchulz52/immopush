package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.Searcher;
import de.bambussoft.immopush.repo.FoundUrl;
import de.bambussoft.immopush.repo.SearchRepository;
import de.bambussoft.immopush.repo.SearchRequest;
import de.bambussoft.immopush.repo.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchConfiguration {

    private final SearchRepository searchRepository;
    private final Searcher searcher;
    private final UrlRepository urlRepository;

    public SearchConfiguration(SearchRepository searchRepository, Searcher searcher, UrlRepository urlRepository) {
        this.searchRepository = searchRepository;
        this.searcher = searcher;
        this.urlRepository = urlRepository;
    }

    public void addSearch(String url, String chatId) {
        searchRepository.save(new SearchRequest(url, chatId));
        List<URL> urls = searcher.findOn(url);
        urlRepository.saveAll(urls.stream().map(u -> new FoundUrl(u.toString(), chatId)).collect(Collectors.toList()));
    }

    public String allToPrint() {
        return searchRepository.findAll().stream().map(s -> s.getId() + " " + s.getUrl()).collect(Collectors.joining("\n"));
    }

    public boolean delete(long id) {
        if (!searchRepository.existsById(id)) {
            return false;
        }
        searchRepository.deleteById(id);
        return true;
    }
}
