package com.vincenzo.bikehub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


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

    @ManyToOne
    private ParkingLot currentParkingLot;

    @ElementCollection(targetClass = com.vincenzo.bikehub.server.gen.model.BicycleCategory.class)
    @Enumerated(EnumType.STRING)
    private Set<com.vincenzo.bikehub.server.gen.model.BicycleCategory> categories = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Equipment> equipments;

    // index
    @Column(nullable = false, unique = true)
    private String chassisId;

    @Column(nullable = false)
    private com.vincenzo.bikehub.server.gen.model.BicycleStatus status = com.vincenzo.bikehub.server.gen.model.BicycleStatus.AVAILABLE;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column
    private Long totalRentTimeInSeconds = 0L;

    @Column(nullable = false)
    private Float hourlyPrice;

    @CreatedDate
    private Instant registeredAt;

    @Transient
    public List<UUID> getEquipmentsIds() {
        if (equipments == null) {
            return List.of();
        }
        return equipments.stream()
            .map(Equipment::getId)
            .collect(Collectors.toList());
    }
}
