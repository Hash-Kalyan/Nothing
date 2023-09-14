package com.application.nothing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    private String message;

    public UserAlreadyExistsException() {

    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
        this.message = msg;

    }

}
