package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.mapper.RentalMapper;
import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.server.gen.controller.RentalApi;
import com.vincenzo.bikehub.server.gen.model.*;
import com.vincenzo.bikehub.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RentalController implements RentalApi {

    private final RentalMapper rentalMapper;
    private final RentalService rentalService;

    public RentalController(RentalMapper rentalMapper, RentalService rentalService) {
        this.rentalMapper = rentalMapper;
        this.rentalService = rentalService;
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> createRental(CreateRental createRentalRequest) {
        Rental serializedRentalModel = rentalMapper.createRentalToModel(createRentalRequest);
        Rental createdRentalModel = rentalService.createRental(serializedRentalModel);
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(createdRentalModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> retrieveRental(UUID rentalId) {
        Rental rental = rentalService.getRentalModel(rentalId);
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> updateRental(UUID rentalId, UpdateRental updateRentalRequest) {
        Rental serializedRentalModel = rentalMapper.updateRentalRequestToModel(updateRentalRequest);
        Rental rental = rentalService.updateRental(rentalId, serializedRentalModel);
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteRental(UUID rentalId) {
        rentalService.deleteRental(rentalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> pickupRental(UUID rentalId) {
        Rental rental = rentalService.pickupRental(rentalId);
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return  ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> returnRental(UUID rentalId, ReturnRentalDetails returnRentalDetails) {
        Rental rental = rentalService.returnRental(rentalId, returnRentalDetails.getReturnParkingLotName());
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return  ResponseEntity.ok(response);
    }
}
