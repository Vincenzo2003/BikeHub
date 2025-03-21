package com.vincenzo.bikehub.repository;

import com.vincenzo.bikehub.entity.Bicycle;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, UUID> {

    boolean existsByChassisId(String chassisId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Bicycle> findByIdWithPessimisticLock(UUID id);

}
