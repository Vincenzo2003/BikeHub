package com.vincenzo.bikehub.service.payment;

import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface IPaymentStrategy {

    boolean pay(Account user, Float amount);

    default PaymentMethod retrievePaymentDetails(Account user) {
        List<PaymentMethod> paymentMethods = user.getPaymentMethods();
        return paymentMethods.stream().filter(paymentMethod -> paymentMethod.getType() == getPaymentType()).findFirst().orElse(null);
    }


    com.vincenzo.bikehub.server.gen.model.PaymentType getPaymentType();
}