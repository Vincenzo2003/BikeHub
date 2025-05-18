package com.vincenzo.bikehub.service.payment;


import com.vincenzo.bikehub.models.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CashPaymentStrategy implements IPaymentStrategy {

    @Override
    public boolean pay(Account user, Float amount) {
        log.info("Payment of {} EUR in cash for user {}", amount, user.getUsername());
        return true;
    }

    @Override
    public com.vincenzo.bikehub.server.gen.model.PaymentType getPaymentType() {
        return com.vincenzo.bikehub.server.gen.model.PaymentType.CASH;
    }
}
