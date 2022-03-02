package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.fetch.parser.EbayKleinanzeigenParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class EbayKleinanzeigenParserTest extends ParserTest {

    EbayKleinanzeigenParser sut;

    List<String> expected1143 = new ArrayList<>() {{
        // is-topads are excluded
        //add("https://www.ebay-kleinanzeigen.de/s-anzeige/sicheres-familienglueck-im-gruenen-bald-ihr-neues-zuhause-in-lange/1926529727-208-1863");
        //add("https://www.ebay-kleinanzeigen.de/s-anzeige/doppelhaushaelfte-ab-387-188-pro-baufamilie-zzgl-grundstueck-mit-grundstuecksservice-auch-andere-standorte-moeglich/2012192541-208-2119");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/einfamilienhaus-mit-einliegerwohnung/2033116693-208-1309");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/neues-zuhause-gesucht-hueckelhoven-umgebung-bitte-lesen-/2033116081-208-1207");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/exklusives-grundstueck-im-sueden-von-dortmund-fuer-ihr-traumhaus/2033115200-208-1090");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/einfamilienhaus-freistehende/2033114064-208-1556");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/resthof-in-alleinlage-sanierungsobjekt-abrissobjekt-/2033113089-208-1469");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/arbeit-und-privat-unter-einem-dach/1991997239-208-1552");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/neues-zuhause-gesucht-wegberg-umgebung-bitte-lesen-/2033112320-208-1190");
        add("https://www.ebay-kleinanzeigen.de/s-anzeige/wohntraum-selbst-gestalten-raumwunder-in-leitmar/2033108396-208-1359");
    }};

    @BeforeEach
    void setup() {
        sut = new EbayKleinanzeigenParser();
    }

    @Test
    void parse() {
        test(sut, expected1143, "ebay_kl_1143_shortend");
    }

    @Test
    void ignoreAlternatives() {
        test(sut, Collections.emptyList(), "ebay_kl_with_alternatives");
    }

}