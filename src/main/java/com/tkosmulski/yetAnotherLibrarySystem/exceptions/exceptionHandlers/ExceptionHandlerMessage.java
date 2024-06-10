package com.tkosmulski.yetAnotherLibrarySystem.exceptions.exceptionHandlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public class ExceptionHandlerMessage {
    String message;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:dd")
    @Temporal(TemporalType.DATE)
    Date timeStamp;

    public ExceptionHandlerMessage(String message) {
        this.message = message;
        this.timeStamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
