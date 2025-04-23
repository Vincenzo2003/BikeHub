package com.vincenzo.bikehub.entity;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.server.gen.model.BicycleCategory;
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

    @ElementCollection(targetClass = BicycleCategory.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "categories")
    private Set<BicycleCategory> categories = new HashSet<>(); // Initialize to avoid NullPointerException

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Equipment> equipments;

    // index
    @Column(nullable = false, unique = true)
    private String chassisId;

    @Column(nullable = false)
    private BicycleStatus status = BicycleStatus.AVAILABLE;

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
