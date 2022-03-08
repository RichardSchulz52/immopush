package de.bambussoft.immopush.send;

import de.bambussoft.immopush.repo.FilterEntry;
import de.bambussoft.immopush.repo.SearchRequest;

import java.util.List;

public class PrintFormatter {

    public static String details(List<SearchRequest> searchRequests, List<FilterEntry> filterEntries) {
        StringBuilder sb = new StringBuilder();
        for (SearchRequest searchRequest : searchRequests) {
            sb.append(searchRequest.getSearchName()).append("\n");
            filterEntries.stream()
                    .filter(f -> f.getSearchName().equals(searchRequest.getSearchName()))
                    .map(PrintFormatter::filter).forEach(s -> sb.append(s).append("\n"));
            sb.append(searchRequest.getUrl()).append("\n");
        }
        String details = sb.toString();
        if (details.isBlank()) {
            details = "No entries";
        }
        return details;
    }

    private static String filter(FilterEntry f) {
        return f.getAttribute() + " " + f.getFilterRelation() + " " + f.getValue();
    }

}
