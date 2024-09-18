package com.bookmanager.app.exception;

public class UniqueIsbnException extends RuntimeException {
    public UniqueIsbnException(String message) {
        super(message);
    }
}
