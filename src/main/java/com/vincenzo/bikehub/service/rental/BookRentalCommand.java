package com.vincenzo.bikehub.service.rental;

import com.vincenzo.bikehub.models.Rental;
import org.springframework.beans.factory.annotation.Autowired;


public class BookRentalCommand implements Command<Rental> {

    private final RentalService rentalService;
    private final Rental rental;
    private final String username;

    public BookRentalCommand(
            RentalService rentalService,
            Rental rental,
            String username
    ) {
        this.rentalService = rentalService;
        this.rental = rental;
        this.username = username;
    }

    @Override
    public Rental execute() {
        return rentalService.createRental(rental, username);
    }
}
