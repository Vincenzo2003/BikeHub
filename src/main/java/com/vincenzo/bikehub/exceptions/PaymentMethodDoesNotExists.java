package com.vincenzo.bikehub.exceptions;

import com.vincenzo.bikehub.server.gen.model.PaymentType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class PaymentMethodDoesNotExists extends ResponseStatusException {
    public PaymentMethodDoesNotExists(PaymentType paymentType) {
        super(HttpStatus.NOT_FOUND, "Payment method with type %s not found.".formatted(paymentType));
    }
}