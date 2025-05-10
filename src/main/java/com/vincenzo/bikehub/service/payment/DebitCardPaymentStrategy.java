package com.vincenzo.bikehub.service.payment;

import com.vincenzo.bikehub.enums.PaymentType;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;

public class DebitCardPaymentStrategy implements IPaymentStrategy {
    @Override
    public boolean pay(Account user, Float amount) {
        return false;
    }

    @Override
    public PaymentMethod retrievePaymentDetails(Account user) {
        return null;
    }

    @Override
    public PaymentType getPaymentType() {
        return null;
    }
}
