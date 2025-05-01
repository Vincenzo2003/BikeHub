package com.vincenzo.bikehub.service;


import com.vincenzo.bikehub.entity.Bicycle;
import com.vincenzo.bikehub.exceptions.*;
import com.vincenzo.bikehub.mapper.RentalMapper;
import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.repository.RentalRepository;
import com.vincenzo.bikehub.server.gen.model.BicycleStatus;
import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RentalService {

    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;
    private final BicycleService bicycleService;
    private final ParkingLotService parkingLotService;

    public RentalService(RentalMapper rentalMapper, RentalRepository rentalRepository, BicycleService bicycleService, ParkingLotService parkingLotService) {
        this.rentalMapper = rentalMapper;
        this.rentalRepository = rentalRepository;
        this.bicycleService = bicycleService;
        this.parkingLotService = parkingLotService;
    }

    @Transactional
    public Rental createRental(Rental rental){
        UUID bicycleToRentId = rental.getBicycleId();
        Bicycle bicycleToRentEntity = bicycleService.getBicycleEntity(bicycleToRentId);
        if (bicycleToRentEntity.getStatus() != BicycleStatus.AVAILABLE) {
            throw new IllegalStateException("Bicycle is not available for renting. Status: " + bicycleToRentEntity.getStatus());
        }
        com.vincenzo.bikehub.entity.Rental rentalEntity = new com.vincenzo.bikehub.entity.Rental();
        rentalEntity.setBicycle(bicycleToRentEntity);
        bicycleService.setBicycleStatus(bicycleToRentId, BicycleStatus.RENTED);
        rentalEntity.setPickUpParkingLot(bicycleToRentEntity.getCurrentParkingLot());
        String returnParkingLotName = rental.getReturnParkingLotName();
        if (returnParkingLotName != null) {
            rentalEntity.setReturnParkingLot(parkingLotService.getParkingLotEntity(returnParkingLotName));
        }
        rentalEntity.setStatus(RentalStatus.CREATED);
        try {
            rentalEntity = rentalRepository.saveAndFlush(rentalEntity);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalSavingException();
        }
        return rentalMapper.entityToModel(rentalEntity);
    }

    public Rental getRentalModel(UUID rentalId) {
        com.vincenzo.bikehub.entity.Rental rentalEntity = getRentalEntity(rentalId);
        return rentalMapper.entityToModel(rentalEntity);
    }

    public com.vincenzo.bikehub.entity.Rental getRentalEntity(UUID rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(RentalNotFoundException::new);
    }

    public void setRentalStatus(UUID rentalId, RentalStatus status) {
        com.vincenzo.bikehub.entity.Rental rentalEntity = getRentalEntity(rentalId);
        if (status == RentalStatus.IN_PROGRESS){
            rentalEntity.setPickUpParkingLot(bicycleService.getBicycleEntity(rentalEntity.getBicycle().getId()).getCurrentParkingLot());
        }
        rentalEntity.setStatus(status);
        try {
            rentalRepository.saveAndFlush(rentalEntity);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalSavingException();
        }
    }
    public Rental updateRental(UUID rentalId, Rental rental) {
        com.vincenzo.bikehub.entity.Rental existingRental = getRentalEntity(rentalId);
        if (rental.getReturnParkingLotName() != null) {
            existingRental.setReturnParkingLot(parkingLotService.getParkingLotEntity(rental.getReturnParkingLotName()));
        }
        if (rental.getStatus() != null) {
            existingRental.setStatus(rental.getStatus());
        }
        if (rental.getTotalPrice() != null){
            existingRental.setTotalPrice(rental.getTotalPrice());
        }
        if (rental.getMileage() != null){
            existingRental.setMileage(rental.getMileage());
        }
        com.vincenzo.bikehub.entity.Rental savedRental;
        try {
            savedRental = rentalRepository.saveAndFlush(existingRental);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalSavingException();
        }
        return rentalMapper.entityToModel(savedRental);
    }

    @Transactional
    public void deleteRental(UUID rentalId) {
        com.vincenzo.bikehub.entity.Rental existingRental =
                rentalRepository.findByIdWithPessimisticLock(rentalId)
                        .orElseThrow(RentalNotFoundException::new);

        if (existingRental.getStatus() == RentalStatus.IN_PROGRESS) {
            throw new IllegalStateException("Cannot delete a rental in progress");
        }
        UUID rentedBicycleId = existingRental.getBicycle().getId();
        bicycleService.setBicycleStatus(rentedBicycleId, BicycleStatus.AVAILABLE);

        try {
            rentalRepository.delete(existingRental);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalDeletingException();
        }

    }
}
