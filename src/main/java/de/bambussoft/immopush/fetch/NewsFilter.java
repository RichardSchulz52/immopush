package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.repo.UrlRepository;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class NewsFilter {

    private final UrlRepository urlRepository;

    public NewsFilter(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<URL> filter(List<URL> strip, String chatId) {
        Map<String, List<URL>> urlsPerHost = strip.stream().map(URL::getHost).distinct().collect(toMap(host -> host, host -> strip.stream().filter(url -> url.getHost().equals(host)).collect(Collectors.toList())));
        List<URL> news = urlsPerHost.keySet().stream().flatMap(h -> urlsPerHost.get(h).stream().filter(url -> !urlRepository.existsByUrlAndChatId(url.toString(), chatId))).collect(Collectors.toList());
        return news.stream().map(StingEqualsUrl::new).distinct().map(StingEqualsUrl::getUrl).collect(Collectors.toList());
    }

    static class StingEqualsUrl {
        private final URL url;

        public StingEqualsUrl(URL url) {
            this.url = url;
        }

        public URL getUrl() {
            return url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StingEqualsUrl that = (StingEqualsUrl) o;
            return Objects.equals(url.toString(), that.url.toString());
        }

        @Override
        public int hashCode() {
            return Objects.hash(url.toString());
        }
    }
}
