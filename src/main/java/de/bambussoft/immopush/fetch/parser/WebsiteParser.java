package de.bambussoft.immopush.fetch.parser;

import de.bambussoft.immopush.fetch.DetailedOffer;

import java.net.URL;
import java.util.List;

public interface WebsiteParser {
    List<URL> parse(String html);

    DetailedOffer details(String searchName, URL url, String offerHtml);
}
