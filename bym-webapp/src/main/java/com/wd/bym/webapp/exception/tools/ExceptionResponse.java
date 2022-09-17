package com.wd.bym.webapp.exception.tools;

import java.io.Serial;
import java.io.Serializable;

public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 882981147341196646L;

    private final String message;
    private final String title;
    private String exceptionMessage;
    private Class<?> exceptionType;

    public ExceptionResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ExceptionResponse(String title, String message, Exception exceptionSource) {
        this(title, message);
        if(exceptionSource != null) {
            this.exceptionMessage = exceptionSource.getMessage();
            this.exceptionType = exceptionSource.getClass();
        }
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public Class<?> getExceptionType() {
        return exceptionType;
    }
}
