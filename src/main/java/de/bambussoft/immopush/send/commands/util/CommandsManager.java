package de.bambussoft.immopush.send.commands.util;

import com.pengrad.telegrambot.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CommandsManager {

    private final Map<String, ChatCommand<?>> identToCommand = new HashMap<>();
    private final Map<ChatCommand<?>, Consumer<HasMessage>> knownCommands = new HashMap<>();

    public <P extends HasMessage, C extends ChatCommand<P>> void registerCommand(C command, Consumer<P> callback) {
        identToCommand.put(command.commandIdentifier(), command);
        knownCommands.put(command, (Consumer<HasMessage>) callback);
    }

    public void callFor(Message telegramMessage) {
        String message = telegramMessage.text();
        if (!message.startsWith("/") && message.length() > 1) {
            return;
        }
        String ident = getCommandIdent(message);
        ChatCommand<?> chatCommand = identToCommand.get(ident);
        if (chatCommand == null) {
            throw new CommandException(ident + " is not a known command");
        }
        String params = getParams(message);
        HasMessage paramObj = chatCommand.mapToParams(telegramMessage, params);
        Consumer<HasMessage> consumer = knownCommands.get(chatCommand);
        consumer.accept(paramObj);
    }

    private String getParams(String message) {
        int startOfParams = message.indexOf(" ");
        if (startOfParams == -1) {
            return null;
        } else {
            return message.substring(startOfParams + 1);
        }
    }

    private String getCommandIdent(String message) {
        int firstSpace = message.indexOf(" ");
        if (firstSpace == -1) {
            return message.substring(1);
        } else {
            return message.substring(1, firstSpace);
        }
    }
}
