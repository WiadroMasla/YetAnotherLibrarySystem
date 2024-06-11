package com.tkosmulski.yetAnotherLibrarySystem.exceptions.exceptionHandlers;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.IllegalNegativeValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnElementNotFoundException(ElementNotFoundException e) {
        return new ResponseEntity<>(new ExceptionHandlerMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnElementAlreadyExistsException(ElementAlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionHandlerMessage(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalNegativeValueException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnIllegalNegativeValueException(IllegalNegativeValueException e) {
        return new ResponseEntity<>(new ExceptionHandlerMessage(e.getMessage()), HttpStatus.CONFLICT);
    }
}
