package de.bambussoft.immopush;

import de.bambussoft.immopush.repo.SearchRepository;
import de.bambussoft.immopush.repo.SearchRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SearchConfiguration {

    private final SearchRepository searchRepository;

    public SearchConfiguration(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void addSearch(String url) {
        searchRepository.save(new SearchRequest(url));
    }

    public String allToPrint() {
        return searchRepository.findAll().stream().map(s -> s.getId() + " " + s.getUrl()).collect(Collectors.joining("\n"));
    }
}
