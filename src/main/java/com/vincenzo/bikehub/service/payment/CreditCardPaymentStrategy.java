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
        String username = user.getUsername();
        boolean paymentSuccessful = processCreditCardPayment(amount, paymentDetails, username);
        if (paymentSuccessful) {
            log.info("Payment with credit card of user {} successful.", username);
        } else {
            log.info("Payment with credit card of user {} refused.", username);
        }
        return paymentSuccessful;
    }

    public boolean processCreditCardPayment(Float amount, PaymentMethod paymentDetails, String username) {
        log.info("Try to pay {} EUR with credit card of user {} with holder {}", amount, username, paymentDetails.getHolder());
        return Math.random() < 0.5;
    }


    @Override
    public com.vincenzo.bikehub.server.gen.model.PaymentType getPaymentType() {
        return com.vincenzo.bikehub.server.gen.model.PaymentType.CREDIT_CARD;
    }
}