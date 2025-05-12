package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class PaymentMethodSavingException extends ResponseStatusException {
    public PaymentMethodSavingException() {
        super(HttpStatus.BAD_REQUEST, "Could not save payment method.");
    }
}
