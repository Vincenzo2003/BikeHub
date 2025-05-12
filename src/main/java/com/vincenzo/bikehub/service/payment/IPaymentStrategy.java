package com.vincenzo.bikehub.service.payment;

import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
import org.springframework.stereotype.Component;


@Component
public interface IPaymentStrategy {

    boolean pay(Account user, Float amount);

    PaymentMethod retrievePaymentDetails(Account user);

    com.vincenzo.bikehub.server.gen.model.PaymentType getPaymentType();
}