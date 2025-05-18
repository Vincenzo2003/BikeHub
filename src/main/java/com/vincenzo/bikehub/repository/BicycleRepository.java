package com.vincenzo.bikehub.repository;

import com.vincenzo.bikehub.entity.Bicycle;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, UUID> {

    boolean existsByChassisId(String chassisId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Bicycle b WHERE b.id = :id")
    Optional<Bicycle> findByIdWithPessimisticLock(@Param("id") UUID id);

    @Query("SELECT SUM(b.totalRentTimeInSeconds) FROM Bicycle b")
    Long getTotalRentTime();

    @Query("SELECT SUM(b.totalRentTimeInSeconds) FROM Bicycle b WHERE :category MEMBER OF b.categories")
    Long getTotalRentTimeByCategory(com.vincenzo.bikehub.server.gen.model.BicycleCategory category);
}
