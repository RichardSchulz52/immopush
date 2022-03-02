package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.Searcher;
import de.bambussoft.immopush.repo.FoundUrl;
import de.bambussoft.immopush.repo.SearchRepository;
import de.bambussoft.immopush.repo.SearchRequest;
import de.bambussoft.immopush.repo.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());
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
        List<FoundUrl> newUrls = urls.stream()
                .filter(u -> !urlRepository.existsByUrlAndChatId(u.toString(), chatId))
                .map(u -> new FoundUrl(u.toString(), chatId))
                .distinct().collect(Collectors.toList());
        logger.warn("No url found for adding url {} on chatId {}", url, chatId);
        urlRepository.saveAll(newUrls);
    }

    public String allToPrint(String chatId) {
        return searchRepository.findAllByChatId(chatId).stream().map(s -> s.getId() + " " + s.getUrl()).collect(Collectors.joining("\n"));
    }

    public boolean delete(long id) {
        if (!searchRepository.existsById(id)) {
            return false;
        }
        searchRepository.deleteById(id);
        return true;
    }
}
