package com.vincenzo.bikehub.repository;

import com.vincenzo.bikehub.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

}
