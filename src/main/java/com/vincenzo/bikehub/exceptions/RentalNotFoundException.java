package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class RentalNotFoundException extends ResponseStatusException {
  public RentalNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Rental not found.");
  }
}
