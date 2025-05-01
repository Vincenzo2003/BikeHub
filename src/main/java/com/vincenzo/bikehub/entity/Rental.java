package com.vincenzo.bikehub.entity;

import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class Rental{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    private Bicycle bicycle;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @ManyToOne
    private ParkingLot pickUpParkingLot;

    @ManyToOne
    private ParkingLot returnParkingLot;

    @Column(nullable = false)
    private RentalStatus status;

    @CreatedDate
    private Instant createdAt;

    @Column
    private Instant startedAt;

    @Column
    private Instant finishedAt;

    @Column
    private Float totalPrice;

    @Column
    private Float mileage;

}
