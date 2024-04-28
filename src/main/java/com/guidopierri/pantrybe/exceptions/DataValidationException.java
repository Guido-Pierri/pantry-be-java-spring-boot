package com.guidopierri.pantrybe.exceptions;

public class DataValidationException extends RuntimeException{
    public DataValidationException() {
    }

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, Throwable t) {
        super(message, t);
    }
}
