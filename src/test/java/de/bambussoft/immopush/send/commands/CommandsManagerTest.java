package de.bambussoft.immopush.send.commands;

import com.pengrad.telegrambot.model.Message;
import de.bambussoft.immopush.send.commands.util.CommandException;
import de.bambussoft.immopush.send.commands.util.CommandsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandsManagerTest {

    CommandsManager sut;
    Message mockMessage;

    @BeforeEach
    void setup() {
        sut = new CommandsManager();
        mockMessage = mock(Message.class);
    }

    @Test
    void testAddCommand() {
        String name = "region";
        String url = "www.immonet.de/suche?foo=bar";
        when(mockMessage.text()).thenReturn("/add " + name + " " + url);
        sut.registerCommand(new AddCommand(), addParam -> {
            assertEquals(name, addParam.getName());
            assertEquals(url, addParam.getUrl());
        });
        sut.callFor(mockMessage);
    }

    @Test
    void displayCommand() {
        AtomicBoolean called = new AtomicBoolean(false);
        when(mockMessage.text()).thenReturn("/display");
        sut.registerCommand(new DisplayCommand(), v -> called.set(true));
        sut.callFor(mockMessage);
        assertTrue(called.get());
    }

    @Test
    void deleteUser() {
        long userId = 3274526L;
        when(mockMessage.text()).thenReturn("/deleteUser " + userId);
        sut.registerCommand(new DeleteUserCommand(), param -> {
            assertEquals(userId, param.getUserId());
        });
        sut.callFor(mockMessage);
    }

    @Test
    void unknownCommand() {
        AtomicBoolean called = new AtomicBoolean(false);
        when(mockMessage.text()).thenReturn("/unknowncommand");
        sut.registerCommand(new DisplayCommand(), v -> called.set(true));
        assertThrows(CommandException.class, () -> sut.callFor(mockMessage));
    }
}