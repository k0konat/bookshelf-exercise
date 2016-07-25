package com.intuit.craft.exception;

public class MissingArgumentException extends IllegalArgumentException {

    public MissingArgumentException(String message) {
        super(message);
    }
}
