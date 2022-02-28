package de.bambussoft.immopush.fetch.parser;

import java.net.URL;
import java.util.List;

public interface WebsiteParser {
    List<URL> parse(String html);
}
