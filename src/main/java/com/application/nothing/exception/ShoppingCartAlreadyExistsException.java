package com.application.nothing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class ShoppingCartAlreadyExistsException extends RuntimeException {
    public ShoppingCartAlreadyExistsException(String message)  {
        super(message);
    }
}
