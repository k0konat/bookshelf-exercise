package com.kkexample.craft.exception;

/**
 * The Class ItemNotFoundException.
 * 
 * @author k0konat.
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * Instantiates a new item not found exception.
     *
     * @param message the message
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
