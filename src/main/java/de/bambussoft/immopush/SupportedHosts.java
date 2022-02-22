package de.bambussoft.immopush;

public class SupportedHosts {
    public static String IMMOWELT = "www.immowelt.de";

    public static boolean isSupported(String host) {
        return host.equals(IMMOWELT);
    }
}
