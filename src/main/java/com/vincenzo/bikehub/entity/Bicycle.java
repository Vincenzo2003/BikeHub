package com.vincenzo.bikehub.entity;

import com.vincenzo.bikehub.enums.BicycleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class Bicycle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @OneToOne(cascade = {CascadeType.PERSIST}, optional = false)
    private ParkingLot currentParkingLot;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<Category> categories;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Equipment> equipments;

    // index
    @Column(nullable = false, unique = true)
    private String chassisId;

    @Column(nullable = false)
    private BicycleStatus status;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column
    private Float totalRentTime;

    @Column(nullable = false)
    private Float hourlyPrice;

    @CreatedDate
    private Instant registeredAt;
}
