package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RentalStatusException extends ResponseStatusException {
  public RentalStatusException() {
    super(HttpStatus.CONFLICT, "Rental is not in the correct status for this action.");
  }
}
