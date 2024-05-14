package com.guidopierri.pantrybe.exceptions;

public class DabasException extends RuntimeException {
    public DabasException() {
    }

    public DabasException(String message) {
        super(message);
    }

    public DabasException(String message, Throwable t) {
        super(message, t);
    }
}

