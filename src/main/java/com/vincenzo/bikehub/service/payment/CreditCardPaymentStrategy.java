package com.vincenzo.bikehub.service.payment;

import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CreditCardPaymentStrategy implements IPaymentStrategy {


    @Override
    public boolean pay(Account user, Float amount) {
        PaymentMethod paymentDetails = retrievePaymentDetails(user);
        log.info("Try to pay {} EUR with credit card of user {}", amount, user.getUsername());
        boolean paymentSuccessful = processCreditCardPayment(amount, paymentDetails);
        if (paymentSuccessful) {
            log.info("Payment with credit card of user {} successful.", user.getUsername());
        } else {
            log.info("Payment with credit card of user {} refused.", user.getUsername());
        }
        return paymentSuccessful;
    }

    @Override
    public PaymentMethod retrievePaymentDetails(Account user) {
        return null;
    }

    public boolean processCreditCardPayment(Float amount, PaymentMethod paymentDetails) {
        return false;
    }


    @Override
    public com.vincenzo.bikehub.server.gen.model.PaymentType getPaymentType() {
        return com.vincenzo.bikehub.server.gen.model.PaymentType.CREDIT_CARD;
    }
}