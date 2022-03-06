package de.bambussoft.immopush.send.commands.util;

public class CommandParamsParser<T extends ChatCommand<?>> {

    private final String[] params;
    private final T chatCommand;
    private int cursor;

    public CommandParamsParser(String paramsString, T chatCommand) {
        this.chatCommand = chatCommand;
        cursor = -1;
        if (paramsString == null || paramsString.isBlank()) {
            params = new String[0];
        } else {
            params = paramsString.split(" ");
        }
    }

    public String nextStr() {
        return getNext("string");
    }

    public Long nextLong() {
        String aLong = getNext("long");
        try {
            return Long.parseLong(aLong);
        } catch (NumberFormatException e) {
            throw new CommandException("not a long at param position " + cursor);
        }
    }

    private String getNext(String expectedTypeName) {
        try {
            cursor++;
            return params[cursor];
        } catch (IndexOutOfBoundsException ioob) {
            throw new CommandException(expectedTypeName + " expected as parameter at position " + cursor + "\nexpected paramters are: " + chatCommand.expectedParams());
        }
    }


}
