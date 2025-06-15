package com.vincenzo.bikehub.repository;


import com.vincenzo.bikehub.entity.Rental;
import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Rental r WHERE r.id = :id")
    Optional<Rental> findByIdWithPessimisticLock(@Param("id") UUID id);

    Page<Rental> findByStatusInAndAccountUsername(Pageable pageable, Collection<RentalStatus> statuses, String accountUsername);

    Page<Rental> findByAccountUsername(Pageable pageable, String accountUsername);

    Page<Rental> findByStatusIn(Pageable pageable, Collection<RentalStatus> statuses);

}
