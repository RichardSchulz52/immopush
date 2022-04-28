package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.fetch.parser.MobileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MobileParserTest extends ParserTest {

    MobileParser sut;

    List<String> expected = new ArrayList<>() {{
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=333113912&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;fnai=prev&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=296312559&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=342279225&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=341045582&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=328400524&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=339090607&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=343139235&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=340471570&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=341737150&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=340511700&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=336426872&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=341485443&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=343553907&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=338657997&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=338028914&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=342603150&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=340518788&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;fnai=next&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
        add("https://suchen.mobile.de/fahrzeuge/details.html?id=338384009&amp;cn=DE&amp;damageUnrepaired=ALSO_DAMAGE_UNREPAIRED&amp;gn=Hattingen&amp;grossPrice=true&amp;isSearchRequest=true&amp;ll=51.4018117%2C7.1911567&amp;makeModelVariant1.makeId=25200&amp;makeModelVariant1.modelId=9&amp;maxMileage=90000&amp;maxPrice=20000&amp;pageNumber=1&amp;scopeId=C&amp;sortOption.sortBy=relevance&amp;action=topOfPage&amp;top=1:1&amp;searchId=5da816a7-cca0-e239-4fd9-c586fff065ae");
    }};

    @BeforeEach
    void setup() {
        sut = new MobileParser();
    }

    @Test
    void parse() {
        test(sut, expected, "mobile");
    }

}