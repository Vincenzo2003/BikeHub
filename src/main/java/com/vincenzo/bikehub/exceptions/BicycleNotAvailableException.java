package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class BicycleNotAvailableException extends ResponseStatusException {
    public BicycleNotAvailableException() {
        super(HttpStatus.CONFLICT, "Bicycle is not available for renting.");
    }
}
