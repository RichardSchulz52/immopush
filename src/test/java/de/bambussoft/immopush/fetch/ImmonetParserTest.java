package de.bambussoft.immopush.fetch;


import de.bambussoft.immopush.fetch.parser.ImmonetParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ImmonetParserTest extends ParserTest {

    ImmonetParser sut;

    List<String> expected = new ArrayList<>() {{
        add("https://www.immonet.de/angebot/46753107");
        add("https://www.immonet.de/angebot/46753080");
        add("https://www.immonet.de/angebot/46752973");
        add("https://www.immonet.de/angebot/46752939");
        add("https://www.immonet.de/angebot/46752882");
        add("https://www.immonet.de/angebot/46752810");
    }};

    @BeforeEach
    void setup() {
        sut = new ImmonetParser();
    }

    @Test
    void parse() {
        test(sut, expected, "immonet_shortend");
    }
}
