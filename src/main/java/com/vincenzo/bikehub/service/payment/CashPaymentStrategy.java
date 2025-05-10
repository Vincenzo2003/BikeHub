package com.vincenzo.bikehub.service.payment;


import com.vincenzo.bikehub.enums.PaymentType;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
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
    public PaymentMethod retrievePaymentDetails(Account user) {
        return null;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CASH;
    }
}

// TODO: aggiungere controller per paymentmethod che ha due richieste (una post per registrare il metodo di pagamento)
// ed una post per pagare il noleggio, in quest'ultima è necessario specificare il tipo di pagamento in modo tale che il service
// possa utilizzare la corretta strategy.