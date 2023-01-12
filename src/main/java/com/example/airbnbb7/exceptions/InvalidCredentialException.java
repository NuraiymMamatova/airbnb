package com.example.airbnbb7.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException() {
        super();
    }

    public InvalidCredentialException(String message) {
        super(message);
    }
}
