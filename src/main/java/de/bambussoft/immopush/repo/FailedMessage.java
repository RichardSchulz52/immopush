package de.bambussoft.immopush.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FailedMessage {

    @Id
    @GeneratedValue
    private long id;
    private String chatId;
    private String message;

    public FailedMessage() {
    }

    public FailedMessage(String chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "FailedMessage{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
