package de.bambussoft.immopush.fetch;

import de.bambussoft.immopush.fetch.parser.WebsiteParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public abstract class ParserTest {

    public static final Charset CHARSET = StandardCharsets.UTF_8;


    void test(WebsiteParser sut, List<String> expected, String pathToHtml) {
        InputStream inputList = getClass().getClassLoader().getResourceAsStream(pathToHtml);
        String html = toLineList(inputList);
        List<String> foundExposeUrls = sut.parse(html).stream().map(URL::toString).collect(Collectors.toList());
        assertLinesMatch(expected, foundExposeUrls);
    }

    String toLineList(InputStream is) {
        StringBuilder textCollector = new StringBuilder();
        try (InputStreamReader streamReader = new InputStreamReader(is, CHARSET);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                textCollector.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textCollector.toString();
    }
}
