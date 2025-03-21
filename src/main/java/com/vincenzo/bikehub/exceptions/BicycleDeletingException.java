package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class BicycleDeletingException extends ResponseStatusException {
    public BicycleDeletingException() {
      super(HttpStatus.BAD_REQUEST, "Could not delete bicycle.");
    }
}
