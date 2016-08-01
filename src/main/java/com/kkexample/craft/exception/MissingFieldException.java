package com.kkexample.craft.exception;

/**
 * The Class MissingArgumentException.
 * 
 * @author k0konat.
 */
public class MissingFieldException extends IllegalArgumentException {

    /**
     * Instantiates a new missing argument exception.
     *
     * @param message the message
     */
    public MissingFieldException(String message) {
        super(message);
    }
}
