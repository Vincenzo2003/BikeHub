package com.vincenzo.bikehub.service.rental;

import com.vincenzo.bikehub.models.Rental;

import java.util.UUID;

public class ReturnRentalCommand implements Command<Rental> {

    public final RentalService rentalService;
    public final UUID rentalId;
    public final String returnParkingLotName;

    public ReturnRentalCommand(
            RentalService rentalService,
            UUID rentalId,
            String returnParkingLotName
    ) {
        this.rentalService = rentalService;
        this.rentalId = rentalId;
        this.returnParkingLotName = returnParkingLotName;
    }

    @Override
    public Rental execute() {
        return rentalService.returnRental(rentalId, returnParkingLotName);
    }
}
