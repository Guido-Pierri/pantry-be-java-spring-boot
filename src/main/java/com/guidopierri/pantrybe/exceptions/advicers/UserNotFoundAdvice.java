package com.guidopierri.pantrybe.exceptions.advicers;

import com.guidopierri.pantrybe.exceptions.UnauthorizedRequestException;
import com.guidopierri.pantrybe.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedRequestException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unauthorizedRequestFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

}
