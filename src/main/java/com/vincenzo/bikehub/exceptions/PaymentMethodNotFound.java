package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PaymentMethodNotFound extends ResponseStatusException {
    public PaymentMethodNotFound() {
        super(HttpStatus.NOT_FOUND, "Payment method not found.");
    }
}
