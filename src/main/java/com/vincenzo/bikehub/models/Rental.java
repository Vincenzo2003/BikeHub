package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.enums.RentalStatus;
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

    private Instant createdAt;

    private Instant startedAt;

    private Instant finishedAt;

    private Float totalPrice;

    private RentalStatus status;
}
