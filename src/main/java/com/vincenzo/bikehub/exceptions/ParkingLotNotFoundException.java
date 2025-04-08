package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ParkingLotNotFoundException extends ResponseStatusException {
    public ParkingLotNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Parking Lot not found.");
    }
}
