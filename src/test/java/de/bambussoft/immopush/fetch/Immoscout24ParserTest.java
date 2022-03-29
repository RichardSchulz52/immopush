package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.fetch.parser.Immoscout24Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Immoscout24ParserTest extends ParserTest {
    Immoscout24Parser sut;

    List<String> expected = new ArrayList<>() {{
        add("https:/www.immobilienscout24.de/expose/132791156");
        add("https:/www.immobilienscout24.de/expose/132655057");
        add("https:/www.immobilienscout24.de/expose/132628819");
        add("https:/www.immobilienscout24.de/expose/132538590");
        add("https:/www.immobilienscout24.de/expose/132469604");
        add("https:/www.immobilienscout24.de/expose/132295110");
        add("https:/www.immobilienscout24.de/expose/132277032");
        add("https:/www.immobilienscout24.de/expose/132276724");
        add("https:/www.immobilienscout24.de/expose/129426916");
        add("https:/www.immobilienscout24.de/expose/132031096");
        add("https:/www.immobilienscout24.de/expose/131996796");
        add("https:/www.immobilienscout24.de/expose/131971714");
        add("https:/www.immobilienscout24.de/expose/131970609");
        add("https:/www.immobilienscout24.de/expose/131871741");
        add("https:/www.immobilienscout24.de/expose/131695355");
        add("https:/www.immobilienscout24.de/expose/130841074");
        add("https:/www.immobilienscout24.de/expose/130083395");
        add("https:/www.immobilienscout24.de/expose/132513475");
    }};

    @BeforeEach
    void setup() {
        sut = new Immoscout24Parser();
    }

    @Test
    void parse() {
        test(sut, expected, "immoscout24_shortend");
    }
}
