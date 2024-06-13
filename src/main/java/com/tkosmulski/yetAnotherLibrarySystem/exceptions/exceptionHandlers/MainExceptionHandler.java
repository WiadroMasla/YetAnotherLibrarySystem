package com.tkosmulski.yetAnotherLibrarySystem.exceptions.exceptionHandlers;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.IllegalNegativeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MainExceptionHandler {
    Logger logger = LoggerFactory.getLogger(MainExceptionHandler.class);

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnElementNotFoundException(ElementNotFoundException e) {
        logger.info("ElementNotFoundException thrown: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionHandlerMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnElementAlreadyExistsException(ElementAlreadyExistsException e) {
        logger.info("ElementAlreadyExistsException thrown: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionHandlerMessage(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalNegativeValueException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnIllegalNegativeValueException(IllegalNegativeValueException e) {
        logger.info("IllegalNegativeValueException thrown: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionHandlerMessage(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionHandlerMessage> triggerOnHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.info("HttpMessageNotReadableException thrown: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionHandlerMessage("Couldn't parse message. " + e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
