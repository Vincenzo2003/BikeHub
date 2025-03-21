package com.vincenzo.bikehub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class ParkingLot {

    private UUID id;

    private String name;

    private String address;

    private List<Bicycle> parkedBicycles;

}
