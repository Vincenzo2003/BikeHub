package com.vincenzo.bikehub.service.rental;

import com.vincenzo.bikehub.models.Rental;
import org.springframework.beans.factory.annotation.Autowired;


public class BookRentalCommand implements Command<Rental> {

    private final RentalService rentalService;
    private final Rental rental;

    public BookRentalCommand(
            RentalService rentalService,
            Rental rental
    ) {
        this.rentalService = rentalService;
        this.rental = rental;
    }

    @Override
    public Rental execute() {
        return rentalService.createRental(rental);
    }
}
