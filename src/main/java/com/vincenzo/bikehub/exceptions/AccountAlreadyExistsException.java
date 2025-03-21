package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class AccountAlreadyExistsException extends ResponseStatusException {
    public AccountAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "User already exists.");
    }
}
