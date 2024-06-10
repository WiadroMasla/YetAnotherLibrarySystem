package com.tkosmulski.yetAnotherLibrarySystem.exceptions;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String elementName, String keyName, Object keyValue) {
        super(elementName + " with " + keyName + " = " + keyValue + " not found.");
    }
}
