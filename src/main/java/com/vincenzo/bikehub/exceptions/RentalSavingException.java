package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class RentalSavingException extends ResponseStatusException {
    public RentalSavingException() {
        super(HttpStatus.BAD_REQUEST, "Could not save rental.");
    }
}