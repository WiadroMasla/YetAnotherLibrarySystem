package com.tkosmulski.yetAnotherLibrarySystem.exceptions;

public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException(String elementName, String keyName, Object keyValue) {
        super(elementName + " with " + keyName + " = " + keyValue + " already exists.");
    }
}
