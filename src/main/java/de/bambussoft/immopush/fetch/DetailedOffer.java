package de.bambussoft.immopush.fetch;

import java.net.URL;

public class DetailedOffer {

    private final String searchName;
    private final URL url;
    private Integer lotSize;

    public DetailedOffer(String searchName, URL url) {
        this.searchName = searchName;
        this.url = url;
    }

    public String getSearchName() {
        return searchName;
    }

    public URL getUrl() {
        return url;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    public String toMessageString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Found in ").append(searchName).append("\n");
        if (lotSize != null) {
            sb.append("lot size: ").append(lotSize).append("\n");
        }
        sb.append(url);
        return sb.toString();
    }
}
