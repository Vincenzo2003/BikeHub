package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PaymentMethodTypeAlreadyExistsException extends ResponseStatusException {
    public PaymentMethodTypeAlreadyExistsException(com.vincenzo.bikehub.server.gen.model.PaymentType type) {
        super(HttpStatus.BAD_REQUEST, "Payment Method with Type %s already exists.".formatted(type));
    }
}
