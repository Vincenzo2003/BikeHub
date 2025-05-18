package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.mapper.RentalMapper;
import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.server.gen.controller.RentalApi;
import com.vincenzo.bikehub.server.gen.model.*;
import com.vincenzo.bikehub.service.rental.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RentalController implements RentalApi {

    private final RentalMapper rentalMapper;
    private final RentalService rentalService;
    private final CommandExecutor commandExecutor;

    public RentalController(
            RentalMapper rentalMapper,
            RentalService rentalService,
            CommandExecutor commandExecutor
    ) {
        this.rentalMapper = rentalMapper;
        this.rentalService = rentalService;
        this.commandExecutor = commandExecutor;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> createRental(CreateRental createRentalRequest) {
        Rental serializedRentalModel = rentalMapper.createRentalToModel(createRentalRequest);
        Rental createdRentalModel = commandExecutor.executeCommand(new BookRentalCommand(rentalService, serializedRentalModel));
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(createdRentalModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> retrieveRental(UUID rentalId) {
        Rental rental = rentalService.getRental(rentalId);
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> payRental(UUID rentalId, PayRental payRental) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rental rental = commandExecutor.executeCommand(new PayRentalCommand(rentalService, rentalId, payRental.getPaymentType(), userDetails.getUsername()));
        return ResponseEntity.ok(rentalMapper.modelToRental(rental));
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> pickupRental(UUID rentalId) {
        Rental rental = commandExecutor.executeCommand(new PickUpRentalCommand(rentalService, rentalId));
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return  ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Rental> returnRental(UUID rentalId, ReturnRentalDetails returnRentalDetails) {
        Rental rental = commandExecutor.executeCommand(new ReturnRentalCommand(rentalService, rentalId, returnRentalDetails.getReturnParkingLotName()));
        com.vincenzo.bikehub.server.gen.model.Rental response = rentalMapper.modelToRental(rental);
        return  ResponseEntity.ok(response);
    }
}
