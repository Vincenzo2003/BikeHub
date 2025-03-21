package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class BicycleSavingException extends ResponseStatusException {
    public BicycleSavingException() {
        super(HttpStatus.BAD_REQUEST, "Could not save bicycle.");
    }
}
