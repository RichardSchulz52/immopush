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

public class ImmonetParser implements WebsiteParser {

    @Override
    public List<URL> parse(String html) {
        List<URL> urls = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element idResultList = document.getElementById("idResultList");
        Elements estates = idResultList.getElementsByTag("a");

        estates.forEach(a -> {
            if (!a.hasClass("text-225")) {
                return;
            }
            URL href = null;
            try {
                href = new URL("https://" + SupportedHosts.IMMONET + a.attr("href"));
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
        DetailedOffer detailedOffer = new DetailedOffer(searchName, url);
        Document document = Jsoup.parse(offerHtml);
        Element lotElement = document.getElementById("areaid_3");
        if (lotElement != null && lotElement.hasText()) {
            String lotText = lotElement.text();
            String lotValue = lotText.substring(0, lotText.lastIndexOf("."));
            try {
                int lot = Integer.parseInt(lotValue);
                detailedOffer.setLotSize(lot);
            } catch (NumberFormatException ignore) {
            }
        }
        return detailedOffer;
    }
}
