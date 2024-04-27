package com.guidopierri.pantrybe.exceptions;

public class UnauthorizedRequestException extends RuntimeException{
    public UnauthorizedRequestException() {
        super("Unauthorized");
    }

}
