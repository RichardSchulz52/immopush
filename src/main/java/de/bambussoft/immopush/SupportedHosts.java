package de.bambussoft.immopush;

import de.bambussoft.immopush.fetch.parser.EbayKleinanzeigenParser;
import de.bambussoft.immopush.fetch.parser.ImmoWeltParser;
import de.bambussoft.immopush.fetch.parser.ImmonetParser;
import de.bambussoft.immopush.fetch.parser.WebsiteParser;

import java.util.List;

public class SupportedHosts {
    public static String IMMOWELT = "www.immowelt.de";
    public static String EBAY_KLEINANZEIGEN = "www.ebay-kleinanzeigen.de";
    public static String IMMONET = "www.immonet.de";

    public static List<String> ALL = List.of(IMMOWELT, EBAY_KLEINANZEIGEN, IMMONET);

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
        }
        throw new RuntimeException("Unsupported host in system");
    }
}
