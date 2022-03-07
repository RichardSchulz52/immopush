package de.bambussoft.immopush.fetch.filter;

import de.bambussoft.immopush.fetch.DetailedOffer;
import de.bambussoft.immopush.repo.FilterEntry;
import de.bambussoft.immopush.repo.FilterEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomFilterTest {

    public static final String ANY_CHAT_ID = "32567829";
    public static final String SEARCH_1 = "immonetBayern";
    private static final String SEARCH_2 = "immonetSachsen";

    public static final FilterEntry MIN_500 = new FilterEntry(ANY_CHAT_ID, SEARCH_1, FilterAttribute.LOT_SIZE, FilterRelation.MIN, 500);
    private static final Object MAX_700 = new FilterEntry(ANY_CHAT_ID, SEARCH_1, FilterAttribute.LOT_SIZE, FilterRelation.MAX, 700);
    private static final Object MAX_1000 = new FilterEntry(ANY_CHAT_ID, SEARCH_1, FilterAttribute.LOT_SIZE, FilterRelation.MAX, 1000);
    private static final Object S2_MIN_500 = new FilterEntry(ANY_CHAT_ID, SEARCH_2, FilterAttribute.LOT_SIZE, FilterRelation.MIN, 500);
    ;

    private static final DetailedOffer S1_481 = getDetailedOffer(SEARCH_1, "https://www.immonet.de/angebot/46803183", 481);
    private static final DetailedOffer S1_780 = getDetailedOffer(SEARCH_1, "https://www.immonet.de/angebot/05467657", 780);
    private static final DetailedOffer S1_537 = getDetailedOffer(SEARCH_1, "https://www.immonet.de/angebot/59734987", 537);
    private static final DetailedOffer S2_467 = getDetailedOffer(SEARCH_2, "https://www.immonet.de/angebot/72345341", 467);


    public static List<DetailedOffer> offers = new ArrayList<>() {{
        add(S1_481);
        add(S1_780);
        add(S1_537);
        add(S2_467);
    }};

    CustomFilter sut;
    FilterEntryRepository repo;

    @BeforeEach
    void setUp() {
        repo = mock(FilterEntryRepository.class);
        sut = new CustomFilter(repo);
    }

    public static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(Collections.singletonList(MIN_500), List.of(S1_537, S1_780, S2_467)),
                Arguments.of(Collections.singletonList(MAX_700), List.of(S1_481, S1_537, S2_467)),
                Arguments.of(List.of(MIN_500, MAX_700), List.of(S1_537, S2_467)),
                Arguments.of(Collections.singletonList(MAX_1000), List.of(S1_481, S1_537, S1_780, S2_467)),
                Arguments.of(Collections.singletonList(S2_MIN_500), List.of(S1_481, S1_537, S1_780)),
                Arguments.of(List.of(MIN_500, S2_MIN_500), List.of(S1_537, S1_780))
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void filter(List<FilterEntry> filters, List<DetailedOffer> allowedOffers) {
        when(repo.findByChatId(ANY_CHAT_ID)).thenReturn(filters);
        List<DetailedOffer> filtered = sut.filter(ANY_CHAT_ID, offers);
        assert filtered.size() == allowedOffers.size() &&
                filtered.containsAll(allowedOffers) &&
                allowedOffers.containsAll(filtered);
    }

    private static DetailedOffer getDetailedOffer(String searchName, String url, int lotSize) {
        DetailedOffer detailedOffer = null;
        try {
            detailedOffer = new DetailedOffer(searchName, new URL(url));
            detailedOffer.setLotSize(lotSize);
        } catch (MalformedURLException ignore) {
        }
        return detailedOffer;
    }

}