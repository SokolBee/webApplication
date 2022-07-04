package com.sokolov.webApplication.exeption;

import java.util.Collection;

public class NotValidUserException extends RuntimeException{

    public final Collection<String> messages;
    public NotValidUserException(Collection<String> messages) {
        super();
        this.messages = messages;
    }

    public Collection<String> getMessages() {
        return messages;
    }
}
