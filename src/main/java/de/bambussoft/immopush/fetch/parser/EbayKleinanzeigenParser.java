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

public class EbayKleinanzeigenParser implements WebsiteParser {

    @Override
    public List<URL> parse(String html) {
        List<URL> urls = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element unwantedAlternatives = document.getElementById("srchrslt-adtable-altads");
        if (unwantedAlternatives != null) {
            unwantedAlternatives.remove();
        }

        Elements estates = document.getElementsByClass("ad-listitem");


        estates.forEach(e -> {
            if (e.hasClass("is-topad")) {
                return;
            }

            Element a = e.getElementsByTag("a").first();
            if (a != null) {
                URL href = null;
                try {
                    href = new URL("https://" + SupportedHosts.EBAY_KLEINANZEIGEN + a.attr("href"));
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
