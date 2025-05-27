package com.vincenzo.bikehub.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BicyclesPage extends Page {

    public BicyclesPage(Integer totalItems, Integer currentPage, Integer totalPages) {
        super(totalItems, currentPage, totalPages);
    }

    private List<Bicycle> bicycles;

    public BicyclesPage() {
        super();
    }
}
