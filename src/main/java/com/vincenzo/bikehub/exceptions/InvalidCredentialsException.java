package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class InvalidCredentialsException extends ResponseStatusException {
    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Authentication failed. Please check your credentials."
        );
    }
}
