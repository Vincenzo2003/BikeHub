package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.enums.BicycleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class Bicycle {

    private UUID id;

    private ParkingLot currentParkingLot;

    private List<Category> categories;

    private List<Equipment> equipments;

    private String chassisId;

    private BicycleStatus status;

    private String brand;

    private String model;

    private Float totalRentTime;

    private Float hourlyPrice;

    private Instant registeredAt;

}
