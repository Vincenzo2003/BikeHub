package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EquipmentNotFound extends ResponseStatusException {
    public EquipmentNotFound() {
        super(HttpStatus.NOT_FOUND, "Equipment not found.");
    }
}
