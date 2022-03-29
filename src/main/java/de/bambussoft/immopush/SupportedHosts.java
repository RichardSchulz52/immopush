package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.parser.EbayKleinanzeigenParser;
import de.bambussoft.immopush.fetch.parser.ImmoWeltParser;
import de.bambussoft.immopush.fetch.parser.ImmonetParser;
import de.bambussoft.immopush.fetch.parser.Immoscout24Parser;
import de.bambussoft.immopush.fetch.parser.WebsiteParser;

import java.util.List;

public class SupportedHosts {
    public static String IMMOWELT = "www.immowelt.de";
    public static String EBAY_KLEINANZEIGEN = "www.ebay-kleinanzeigen.de";
    public static String IMMONET = "www.immonet.de";
    public static String IMMOSCOUT_24 = "www.immobilienscout24.de";

    public static List<String> ALL = List.of(IMMOWELT, EBAY_KLEINANZEIGEN, IMMONET, IMMOSCOUT_24);

    public static boolean isSupported(String host) {
        return ALL.contains(host);
    }

    public static WebsiteParser getParser(String host) {
        if (host.equals(IMMOWELT)) {
            return new ImmoWeltParser();
        } else if (host.equals(EBAY_KLEINANZEIGEN)) {
            return new EbayKleinanzeigenParser();
        } else if (host.equals(IMMONET)) {
            return new ImmonetParser();
        } else if (host.equals(IMMOSCOUT_24)) {
            return new Immoscout24Parser();
        }
        throw new RuntimeException("Unsupported host in system");
    }
}
