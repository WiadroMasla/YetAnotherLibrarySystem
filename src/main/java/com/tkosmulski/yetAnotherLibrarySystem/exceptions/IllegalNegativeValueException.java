package com.tkosmulski.yetAnotherLibrarySystem.exceptions;

public class IllegalNegativeValueException extends RuntimeException {
    public IllegalNegativeValueException(String key) {
        super("New value of " + key + " cannot be negative.");
    }
}
