package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PaymentStrategyNotImplemented extends ResponseStatusException {
    public PaymentStrategyNotImplemented(com.vincenzo.bikehub.server.gen.model.PaymentType type) {
        super(HttpStatus.BAD_REQUEST, "Payment Strategy for type %s not implemented.".formatted(type));
    }
}
