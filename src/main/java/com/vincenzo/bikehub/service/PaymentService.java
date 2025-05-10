package com.vincenzo.bikehub.service;


import com.vincenzo.bikehub.enums.PaymentType;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import com.vincenzo.bikehub.service.payment.IPaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {

    private final Map<PaymentType, IPaymentStrategy> strategies;
    private final AuthService authService;
    private final RentalService rentalService;

    @Autowired
    public PaymentService(
            List<IPaymentStrategy> paymentStrategies,
            AuthService authService,
            RentalService rentalService
    ) {
        this.authService = authService;
        strategies = paymentStrategies.stream()
                .collect(Collectors.toMap(IPaymentStrategy::getPaymentType, strategy -> strategy));
        this.rentalService = rentalService;
    }

    public boolean processPayment(String username, UUID paymentMethodId, UUID rentalId, PaymentType paymentType) {
        Account user = authService.getAccount(username);
        Rental rental = rentalService.getRental(rentalId);
        RentalStatus rentalStatus = rental.getStatus();
        if (rentalStatus == RentalStatus.PAYED) {
            log.error("Rental {} has already been paid for", rentalId);
            return false; //TODO throw specific exception
        } else if (rentalStatus != RentalStatus.FINISHED) {
            log.error("Rental {} is in incorrect status", rentalId);
            return false;
        }
        Float priceToPay = rental.getTotalPrice();
        IPaymentStrategy paymentStrategy = strategies.get(paymentType);

        if (paymentStrategy == null) {
            log.error("Payment strategy not found for type: {}", paymentType);
            return false;
        }

        return paymentStrategy.pay(user, priceToPay);
    }

    public PaymentMethod addPaymentMethod(String username, PaymentMethod paymentMethod) {
        return null;
    }

    public PaymentMethod updatePaymentMethod(String username, UUID paymentMethodId, PaymentMethod paymentMethod) {
        return null;
    }
}