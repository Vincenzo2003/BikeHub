package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class RentalCreationException extends ResponseStatusException {
    public RentalCreationException() {
        super(HttpStatus.BAD_REQUEST, "Rental creation failed.");
    }
}
