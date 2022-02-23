package de.bambussoft.immopush.fetch;

import java.net.URL;
import java.util.List;

public interface WebsiteParser {
    List<URL> parse(String html);
}
