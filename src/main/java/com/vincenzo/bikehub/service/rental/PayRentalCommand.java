package com.vincenzo.bikehub.service.rental;

import com.vincenzo.bikehub.models.Rental;

import java.util.UUID;

public class PayRentalCommand implements Command<Rental> {
    public final RentalService rentalService;
    public final UUID rentalId;
    public final com.vincenzo.bikehub.server.gen.model.PaymentType paymentType;
    public final String username;

    public PayRentalCommand(
            RentalService rentalService,
            UUID rentalId,
            com.vincenzo.bikehub.server.gen.model.PaymentType paymentType,
            String username
    ) {
        this.rentalService = rentalService;
        this.rentalId = rentalId;
        this.paymentType = paymentType;
        this.username = username;
    }

    @Override
    public Rental execute() {
        return rentalService.payRental(rentalId, paymentType, username);
    }
}
