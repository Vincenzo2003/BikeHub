package com.vincenzo.bikehub.entity;

import com.vincenzo.bikehub.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Rental{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private ParkingLot pickUpParkingLot;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private ParkingLot returnParkingLot;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Bicycle bicycle;

    @CreatedDate
    private Instant createdAt;

    @Column
    private Instant startedAt;

    @Column
    private Instant finishedAt;

    @Column(nullable = false)
    private Float totalPrice;

    @Column(nullable = false)
    private RentalStatus status;

    @Column
    private Float mileage;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private PaymentMethod paymentMethod;
}
