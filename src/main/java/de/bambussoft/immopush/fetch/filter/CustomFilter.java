package de.bambussoft.immopush.fetch.filter;

import de.bambussoft.immopush.fetch.DetailedOffer;
import de.bambussoft.immopush.repo.FilterEntry;
import de.bambussoft.immopush.repo.FilterEntryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomFilter {

    private final FilterEntryRepository filterEntryRepository;

    public CustomFilter(FilterEntryRepository filterEntryRepository) {
        this.filterEntryRepository = filterEntryRepository;
    }

    public List<DetailedOffer> filter(String chatId, List<DetailedOffer> detailedOffers) {
        List<FilterEntry> all = filterEntryRepository.findByChatId(chatId);
        List<DetailedOffer> filtered = new ArrayList<>();
        for (DetailedOffer detailedOffer : detailedOffers) {
            boolean isNotAllowed = all.stream().filter(f -> isFor(f, detailedOffer)).anyMatch(f -> !allowed(f, detailedOffer));
            if (!isNotAllowed) {
                filtered.add(detailedOffer);
            }
        }
        return filtered;

    }

    private boolean isFor(FilterEntry f, DetailedOffer detailedOffer) {
        return f.getSearchName().equals(detailedOffer.getSearchName());
    }

    private boolean allowed(FilterEntry f, DetailedOffer detailedOffer) {
        boolean isAllowed = true;
        switch (f.getAttribute()) {
            case LOT_SIZE:
                if (!valueAllowed(detailedOffer.getLotSize(), f.getValue(), f.getFilterRelation())) {
                    isAllowed = false;
                }
                break;
            default:
                throw new RuntimeException("filter attribute not implemented");
        }
        return isAllowed;
    }

    private boolean valueAllowed(Integer offerValue, int filterValue, FilterRelation relation) {
        if (offerValue == null) {
            return true;
        }
        switch (relation) {
            case MIN:
                return offerValue >= filterValue;
            case MAX:
                return offerValue <= filterValue;
        }
        throw new RuntimeException("relation not implemented");
    }
}
