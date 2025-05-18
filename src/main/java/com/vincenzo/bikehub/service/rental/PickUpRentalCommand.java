package com.vincenzo.bikehub.service.rental;

import com.vincenzo.bikehub.models.Rental;

import java.util.UUID;

public class PickUpRentalCommand implements Command<Rental> {

    public final RentalService rentalService;
    public final UUID rentalId;

    public PickUpRentalCommand(
            RentalService rentalService,
            UUID rentalId
    ) {
        this.rentalService = rentalService;
        this.rentalId = rentalId;
    }

    @Override
    public Rental execute() {
        return rentalService.pickupRental(rentalId);
    }
}
