package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.enums.CategoryType;
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

    private String currentParkingLotName;

    private List<CategoryType> categories;

    private List<UUID> equipmentsIds;

    private String chassisId;

    private BicycleStatus status = BicycleStatus.AVAILABLE;

    private String brand;

    private String model;

    private Float totalRentTime;

    private Float hourlyPrice;

    private Instant registeredAt;

}
