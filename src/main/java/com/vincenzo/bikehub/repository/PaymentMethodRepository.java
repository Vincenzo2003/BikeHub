package com.vincenzo.bikehub.repository;

import com.vincenzo.bikehub.entity.Account;
import com.vincenzo.bikehub.entity.PaymentMethod;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.id = :id")
    Optional<PaymentMethod> findByIdWithPessimisticLock(@Param("id") UUID id);

    List<PaymentMethod> findAllByAccountId(UUID accountId);

    boolean existsByAccountIdAndType(UUID accountId, com.vincenzo.bikehub.server.gen.model.PaymentType paymentType);
}
