package de.bambussoft.immopush.fetch.parser;

import de.bambussoft.immopush.SupportedHosts;
import de.bambussoft.immopush.fetch.DetailedOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Immoscout24Parser implements WebsiteParser {

    public List<URL> parse(String html) {
        List<URL> urls = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements estates = document.getElementsByClass("result-list-entry");


        estates.forEach(e -> {
            Element a = e.getElementsByTag("a").first();
            if (a != null) {
                URL href = null;
                try {
                    href = new URL("https:/" + SupportedHosts.IMMOSCOUT_24 + a.attr("href"));
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                if (href != null) {
                    urls.add(href);
                }
            }
        });
        return urls;
    }

    @Override
    public DetailedOffer details(String searchName, URL url, String offerHtml) {
        return new DetailedOffer(searchName, url);
    }
}
