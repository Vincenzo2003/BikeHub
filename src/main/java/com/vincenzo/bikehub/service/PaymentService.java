package com.vincenzo.bikehub.service;


import com.vincenzo.bikehub.exceptions.*;
import com.vincenzo.bikehub.mapper.PaymentMethodMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;

import com.vincenzo.bikehub.repository.PaymentMethodRepository;
import com.vincenzo.bikehub.server.gen.model.PaymentType;
import com.vincenzo.bikehub.service.payment.IPaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {

    private final Map<com.vincenzo.bikehub.server.gen.model.PaymentType, IPaymentStrategy> strategies;
    private final AuthService authService;
    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public PaymentService(
            List<IPaymentStrategy> paymentStrategies,
            AuthService authService,
            PaymentMethodMapper paymentMethodMapper,
            PaymentMethodRepository paymentMethodRepository) {
        this.authService = authService;
        strategies = paymentStrategies.stream()
                .collect(Collectors.toMap(IPaymentStrategy::getPaymentType, strategy -> strategy));
        this.paymentMethodMapper = paymentMethodMapper;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public void processPayment(com.vincenzo.bikehub.server.gen.model.PaymentType paymentType, Float priceToPay, String username) {
        Account user = authService.getAccount(username);
        if (paymentType != PaymentType.CASH) {
            boolean existsByAccountAndType = paymentMethodRepository.existsByAccountIdAndType(user.getId(), paymentType);
            if (!existsByAccountAndType) {
                throw new PaymentMethodDoesNotExists(paymentType, username);
            }
        }
        IPaymentStrategy paymentStrategy = strategies.get(paymentType);

        if (paymentStrategy == null) {
            log.error("Payment strategy not found for type: {}", paymentType);
            throw new PaymentStrategyNotImplemented(paymentType);
        }

        boolean isPaymentSucceeded = paymentStrategy.pay(user, priceToPay);
        if (!isPaymentSucceeded) {
            log.error("Payment failed for user: {}", username);
            throw new PaymentError();
        }
    }

    public com.vincenzo.bikehub.entity.PaymentMethod getPaymentMethodEntity(UUID paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(PaymentMethodNotFound::new);
    }

    public PaymentMethod getPaymentMethod(UUID paymentMethodId) {
        return paymentMethodMapper.entityToModel(getPaymentMethodEntity(paymentMethodId));
    }

    @Transactional
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod, String username) {
        com.vincenzo.bikehub.entity.Account user = authService.getAccountEntity(username);
        boolean exists = paymentMethodRepository.existsByAccountIdAndType(user.getId(), paymentMethod.getType());
        if (exists) {
            throw new PaymentMethodTypeAlreadyExistsException(paymentMethod.getType());
        }
        com.vincenzo.bikehub.entity.PaymentMethod paymentMethodEntity = paymentMethodMapper.modelToEntity(paymentMethod);
        paymentMethodEntity.setAccount(user);
        try {
            paymentMethodEntity = paymentMethodRepository.saveAndFlush(paymentMethodEntity);
        } catch (DataIntegrityViolationException exc){
            throw new PaymentMethodSavingException();
        }
        return paymentMethodMapper.entityToModel(paymentMethodEntity);
    }

    public List<PaymentMethod> retrievePaymentMethods(String username) {
        Account account = authService.getAccount(username);
        return paymentMethodRepository.findAllByAccountId(account.getId()).stream()
                .map(paymentMethodMapper::entityToModel)
                .collect(Collectors.toList());
    }

    public PaymentMethod retrievePaymentMethod(UUID paymentMethodId) {
        return getPaymentMethod(paymentMethodId);
    }

    @Transactional
    public PaymentMethod updatePaymentMethod(UUID paymentMethodId, PaymentMethod paymentMethod) {
        com.vincenzo.bikehub.entity.PaymentMethod existingPaymentMethod =
                getPaymentMethodEntity(paymentMethodId);

        if (paymentMethod.getCc() != null) {
            existingPaymentMethod.setCc(paymentMethod.getCc());
        }
        if (paymentMethod.getCvc() != null) {
            existingPaymentMethod.setCvc(paymentMethod.getCvc());
        }
        if (paymentMethod.getHolder() != null) {
            existingPaymentMethod.setHolder(paymentMethod.getHolder());
        }
        if (paymentMethod.getType() != null) {
            existingPaymentMethod.setType(paymentMethod.getType());
        }
        if (paymentMethod.getExpireAt() != null) {
            existingPaymentMethod.setExpireAt(paymentMethod.getExpireAt());
        }

        com.vincenzo.bikehub.entity.PaymentMethod savedPaymentMethod;
        try {
            savedPaymentMethod = paymentMethodRepository.saveAndFlush(existingPaymentMethod);
        } catch (DataIntegrityViolationException exc) {
            throw new PaymentMethodSavingException();
        }

        return paymentMethodMapper.entityToModel(savedPaymentMethod);

    }

    @Transactional
    public void deletePaymentMethod(UUID paymentMethodId) {
        com.vincenzo.bikehub.entity.PaymentMethod existingPaymentMethod =
                paymentMethodRepository.findByIdWithPessimisticLock(paymentMethodId)
                        .orElseThrow(PaymentMethodNotFound::new);
        try {
            paymentMethodRepository.delete(existingPaymentMethod);
        } catch (DataIntegrityViolationException exc) {
            throw new PaymentMethodDeletingException();
        }
    }
}