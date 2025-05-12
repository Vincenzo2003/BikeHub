package com.vincenzo.bikehub.controller;


import com.vincenzo.bikehub.mapper.PaymentMethodMapper;
import com.vincenzo.bikehub.models.PaymentMethod;
import com.vincenzo.bikehub.server.gen.controller.PaymentApi;
import com.vincenzo.bikehub.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;
    private final PaymentMethodMapper paymentMethodMapper;

    public PaymentController(
            PaymentService paymentService,
            PaymentMethodMapper paymentMethodMapper
    ) {
        this.paymentService = paymentService;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.PaymentMethod> createPaymentMethod(com.vincenzo.bikehub.server.gen.model.CreatePaymentMethod createPaymentMethod) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PaymentMethod PaymentMethodModel = paymentMethodMapper.createPaymentMethodToModel(createPaymentMethod);
        PaymentMethod createdPaymentMethod = paymentService.createPaymentMethod(PaymentMethodModel, userDetails.getUsername());
        return ResponseEntity.ok(paymentMethodMapper.modelToPaymentMethod(createdPaymentMethod));
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.PaymentMethod> retrievePaymentMethod(UUID paymentMethodId) {
        com.vincenzo.bikehub.models.PaymentMethod retrievedPaymentMethod = paymentService.retrievePaymentMethod(paymentMethodId);
        return ResponseEntity.ok(paymentMethodMapper.modelToPaymentMethod(retrievedPaymentMethod));
    }

    @Override
    public ResponseEntity<List<com.vincenzo.bikehub.server.gen.model.PaymentMethod>> retrievePaymentMethods() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<com.vincenzo.bikehub.models.PaymentMethod> retrievedPaymentMethods = paymentService.retrievePaymentMethods(userDetails.getUsername());
        List<com.vincenzo.bikehub.server.gen.model.PaymentMethod> paymentMethods = new ArrayList<>();
        for (com.vincenzo.bikehub.models.PaymentMethod retrievedPaymentMethod : retrievedPaymentMethods) {
            paymentMethods.add(paymentMethodMapper.modelToPaymentMethod(retrievedPaymentMethod));
        }
        return ResponseEntity.ok(paymentMethods);
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.PaymentMethod> updatePaymentMethod(UUID paymentMethodId, com.vincenzo.bikehub.server.gen.model.UpdatePaymentMethod updatePaymentMethod) {
        com.vincenzo.bikehub.models.PaymentMethod PaymentMethodModel = paymentMethodMapper.updatePaymentMethodToModel(updatePaymentMethod);
        com.vincenzo.bikehub.models.PaymentMethod updatedPaymentMethod = paymentService.updatePaymentMethod(paymentMethodId, PaymentMethodModel);
        return ResponseEntity.ok(paymentMethodMapper.modelToPaymentMethod(updatedPaymentMethod));

    }

    @Override
    public ResponseEntity<Void> deletePaymentMethod(UUID paymentMethodId) {
        paymentService.deletePaymentMethod(paymentMethodId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
