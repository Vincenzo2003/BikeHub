package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class RentalDeletingException extends ResponseStatusException {
    public RentalDeletingException() {
        super(HttpStatus.BAD_REQUEST, "Could not delete rental.");
    }
}
