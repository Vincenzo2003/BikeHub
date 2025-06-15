package com.vincenzo.bikehub.exceptions;

import com.vincenzo.bikehub.server.gen.model.PaymentType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class PaymentMethodDoesNotExists extends ResponseStatusException {
    public PaymentMethodDoesNotExists(PaymentType paymentType, String username) {
        super(HttpStatus.NOT_FOUND, "Payment method with type %s not found for user %s.".formatted(paymentType, username));
    }
}