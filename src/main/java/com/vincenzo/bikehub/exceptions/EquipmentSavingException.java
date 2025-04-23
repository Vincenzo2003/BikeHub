package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class EquipmentSavingException extends ResponseStatusException {
    public EquipmentSavingException() {
        super(HttpStatus.BAD_REQUEST, "Could not save equipment.");
    }
}
