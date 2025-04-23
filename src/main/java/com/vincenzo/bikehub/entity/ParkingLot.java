package com.vincenzo.bikehub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class ParkingLot {

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany
    private List<Bicycle> parkedBicycles;

    @Transient
    public List<UUID> getParkedBicyclesIds() {
        if (parkedBicycles == null) {
            return List.of();
        }
        return parkedBicycles.stream()
                .map(Bicycle::getId)
                .collect(Collectors.toList());
    }
}
