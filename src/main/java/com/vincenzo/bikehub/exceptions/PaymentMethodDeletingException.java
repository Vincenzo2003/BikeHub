package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class PaymentMethodDeletingException extends ResponseStatusException {
    public PaymentMethodDeletingException() {
        super(HttpStatus.BAD_REQUEST, "Could not delete payment method.");
    }
}

