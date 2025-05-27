package com.vincenzo.bikehub.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EquipmentsPage extends Page {

    public EquipmentsPage(Integer totalItems, Integer currentPage, Integer totalPages) {
        super(totalItems, currentPage, totalPages);
    }

    private List<Equipment> equipments;

    public EquipmentsPage() {
        super();
    }
}
