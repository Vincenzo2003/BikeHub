package com.vincenzo.bikehub.service.payment;

import com.vincenzo.bikehub.enums.PaymentType;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
import org.springframework.stereotype.Component;


@Component
public interface IPaymentStrategy {

    boolean pay(Account user, Float amount);

    PaymentMethod retrievePaymentDetails(Account user);

    PaymentType getPaymentType();
}