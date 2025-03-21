package com.vincenzo.bikehub.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class AccountNotFoundException extends ResponseStatusException {
    public AccountNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found.");
    }
}
