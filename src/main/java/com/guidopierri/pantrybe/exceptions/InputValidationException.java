package com.guidopierri.pantrybe.exceptions;

public class InputValidationException extends Exception {

    final String field;

    public InputValidationException(String field, String message) {
        super(field + ": " + message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
