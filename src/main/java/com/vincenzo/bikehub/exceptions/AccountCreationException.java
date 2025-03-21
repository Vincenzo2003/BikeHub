package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountCreationException extends ResponseStatusException {
    public AccountCreationException() {
        super(HttpStatus.BAD_REQUEST, "Unable to create an Account.");
    }
}
