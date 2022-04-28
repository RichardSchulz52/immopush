package de.bambussoft.immopush.fetch.parser;

import de.bambussoft.immopush.fetch.DetailedOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MobileParser implements WebsiteParser {

    @Override
    public List<URL> parse(String html) {
        List<URL> urls = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements cars = document.getElementsByClass("cBox-body--resultitem");
        cars.addAll(document.getElementsByClass("cBox-body--topResultitem"));

        cars.forEach(element -> {
            URL href = null;
            Elements a = element.getElementsByTag("a");
            try {
                href = new URL(a.attr("href"));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            if (href != null) {
                urls.add(href);
            }
        });
        return urls;
    }

    @Override
    public DetailedOffer details(String searchName, URL url, String offerHtml) {
        return new DetailedOffer(searchName, url);
    }
}
