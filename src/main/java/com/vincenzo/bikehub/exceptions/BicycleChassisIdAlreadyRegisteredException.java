package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BicycleChassisIdAlreadyRegisteredException extends ResponseStatusException {
    public BicycleChassisIdAlreadyRegisteredException() {
        super(HttpStatus.CONFLICT, "chassisId already registered.");
    }
}
