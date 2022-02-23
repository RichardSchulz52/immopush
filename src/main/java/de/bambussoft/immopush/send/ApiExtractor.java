package de.bambussoft.immopush.send;

import com.pengrad.telegrambot.model.Message;

public class ApiExtractor {

    public static String userNames(Message message) {
        assert message != null;
        String first = message.from().firstName() != null ? message.from().firstName() : "";
        String last = message.from().lastName() != null ? message.from().lastName() : "";
        String username = message.from().username() != null ? message.from().username() : "";
        return first + ";" + last + ";" + username;
    }

}
