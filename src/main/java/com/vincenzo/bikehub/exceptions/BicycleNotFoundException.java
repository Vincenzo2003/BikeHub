package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BicycleNotFoundException extends ResponseStatusException {
    public BicycleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Bicycle not found.");
    }
}
