package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.SupportedHosts;
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
}
