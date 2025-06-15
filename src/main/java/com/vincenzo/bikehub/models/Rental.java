package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class Rental {

    private UUID id;

    private UUID bicycleId;

    private String pickUpParkingLotName;

    private String returnParkingLotName;

    private UUID paymentMethodId;

    private String accountUsername;

    private RentalStatus status;

    private Instant createdAt;

    private Instant startedAt;

    private Instant finishedAt;

    private Float totalPrice;

    private Float mileage;

}
