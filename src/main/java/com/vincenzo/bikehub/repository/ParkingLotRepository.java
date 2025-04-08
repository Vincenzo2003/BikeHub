package com.vincenzo.bikehub.repository;

import com.vincenzo.bikehub.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, UUID> {

    Optional<ParkingLot> findByName(String name);

    boolean existsByName(String name);

}
