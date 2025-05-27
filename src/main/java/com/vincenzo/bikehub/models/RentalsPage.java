package com.vincenzo.bikehub.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RentalsPage extends Page {

    public RentalsPage(Integer totalItems, Integer currentPage, Integer totalPages) {
        super(totalItems, currentPage, totalPages);
    }

    private List<Rental> rentals;

    public RentalsPage() {
        super();
    }
}
