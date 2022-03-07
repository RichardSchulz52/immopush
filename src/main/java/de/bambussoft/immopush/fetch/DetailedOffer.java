package de.bambussoft.immopush.fetch;

import java.net.URL;
import java.util.Objects;

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

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailedOffer that = (DetailedOffer) o;
        return Objects.equals(searchName, that.searchName) && Objects.equals(url, that.url) && Objects.equals(lotSize, that.lotSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchName, url, lotSize);
    }
}
