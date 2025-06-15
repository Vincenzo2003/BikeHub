package com.vincenzo.bikehub.service.rental;


import com.vincenzo.bikehub.exceptions.*;
import com.vincenzo.bikehub.mapper.RentalMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.models.RentalsPage;
import com.vincenzo.bikehub.repository.RentalRepository;
import com.vincenzo.bikehub.server.gen.model.BicycleStatus;
import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import com.vincenzo.bikehub.service.AuthService;
import com.vincenzo.bikehub.service.BicycleService;
import com.vincenzo.bikehub.service.ParkingLotService;
import com.vincenzo.bikehub.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class RentalService {

    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;
    private final BicycleService bicycleService;
    private final ParkingLotService parkingLotService;
    private final PaymentService paymentService;
    private final AuthService authService;

    public RentalService(
            RentalMapper rentalMapper,
            RentalRepository rentalRepository,
            BicycleService bicycleService,
            ParkingLotService parkingLotService,
            PaymentService paymentService,
            AuthService authService
    ) {
        this.rentalMapper = rentalMapper;
        this.rentalRepository = rentalRepository;
        this.bicycleService = bicycleService;
        this.parkingLotService = parkingLotService;
        this.paymentService = paymentService;
        this.authService = authService;
    }

    private Rental saveRental(Rental rental){
        com.vincenzo.bikehub.entity.Rental rentalEntity = new com.vincenzo.bikehub.entity.Rental();
        rentalEntity.setBicycle(bicycleService.getBicycleEntity(rental.getBicycleId()));
        rentalEntity.setAccount(authService.getAccountEntity(rental.getAccountUsername()));
        rentalEntity.setPickUpParkingLot(parkingLotService.getParkingLotEntity(rental.getPickUpParkingLotName()));
        if (rental.getReturnParkingLotName() != null) {
            rentalEntity.setReturnParkingLot(parkingLotService.getParkingLotEntity(rental.getReturnParkingLotName()));
        }
        rentalEntity.setStatus(rental.getStatus());
        try {
            rentalEntity = rentalRepository.saveAndFlush(rentalEntity);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalSavingException();
        }
        return rentalMapper.entityToModel(rentalEntity);
    }

    @Transactional
    public Rental createRental(Rental rental, String username) {
        UUID bicycleToRentId = rental.getBicycleId();
        Account user = authService.getAccount(username);
        List<RentalStatus> rentalStatuses = new ArrayList<>();
        rentalStatuses.add(RentalStatus.CREATED);
        rentalStatuses.add(RentalStatus.IN_PROGRESS);
        rentalStatuses.add(RentalStatus.FINISHED);
        Page<com.vincenzo.bikehub.entity.Rental> rentalsEntitiesPage = rentalRepository.findByStatusInAndAccountUsername(null, rentalStatuses, username);
        if (!rentalsEntitiesPage.isEmpty()) {
            throw new RentalCreationException();
        }
        Bicycle bicycleToRent = bicycleService.getBicycleModel(bicycleToRentId);
        if (bicycleToRent.getStatus() != BicycleStatus.AVAILABLE) {
            throw new BicycleNotAvailableException();
        }
        Bicycle bicycleToUpdate = new Bicycle();
        bicycleToUpdate.setId(bicycleToRentId);
        bicycleToUpdate.setStatus(BicycleStatus.RENTED);
        Rental rentalToSave = new Rental();
        rentalToSave.setBicycleId(bicycleToRentId);
        rentalToSave.setPickUpParkingLotName(bicycleToRent.getCurrentParkingLotName());
        String returnParkingLotName = rental.getReturnParkingLotName();
        rentalToSave.setAccountUsername(user.getUsername());
        if (returnParkingLotName != null) {
            rentalToSave.setReturnParkingLotName(returnParkingLotName);
            bicycleToUpdate.setCurrentParkingLotName(returnParkingLotName);
        }
        rentalToSave.setStatus(RentalStatus.CREATED);
        bicycleService.updateBicycle(bicycleToRentId, bicycleToUpdate);
        return saveRental(rentalToSave);
    }

    public Rental getRental(UUID rentalId) {
        com.vincenzo.bikehub.entity.Rental rentalEntity = getRentalEntity(rentalId);
        return rentalMapper.entityToModel(rentalEntity);
    }

    public com.vincenzo.bikehub.entity.Rental getRentalEntity(UUID rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(RentalNotFoundException::new);
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
        if (rental.getFinishedAt() != null){
            existingRental.setFinishedAt(rental.getFinishedAt());
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
            throw new RentalDeletingException();
        }
        UUID rentedBicycleId = existingRental.getBicycle().getId();
        bicycleService.setBicycleStatus(rentedBicycleId, BicycleStatus.AVAILABLE);

        try {
            rentalRepository.delete(existingRental);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalDeletingException();
        }

    }

    public Rental pickupRental(UUID rentalId) {
        com.vincenzo.bikehub.entity.Rental rental = getRentalEntity(rentalId);
        if (rental.getStatus() != RentalStatus.CREATED) {
            return rentalMapper.entityToModel(rental);
        }
        rental.setStatus(RentalStatus.IN_PROGRESS);
        rental.setStartedAt(Instant.now());
        rental.getBicycle().setCurrentParkingLot(null);
        try {
            rentalRepository.saveAndFlush(rental);
        } catch (DataIntegrityViolationException exc) {
            throw new RentalSavingException();
        }
        return rentalMapper.entityToModel(rental);
    }

    private Float determinateRentalTotalPrice(Float hourlyPrice, Instant rentalStartedAt, Instant rentalFinishedAt) {
        Long rentalSeconds = Duration.between(rentalStartedAt, rentalFinishedAt).toSeconds();
        Float costPerSecond = hourlyPrice / (60 * 60);
        float price = costPerSecond * rentalSeconds;
        BigDecimal truncatedPrice = new BigDecimal(Float.toString(price)).setScale(2, RoundingMode.HALF_UP);
        return truncatedPrice.floatValue();
    }

    @Transactional
    public Rental returnRental(UUID rentalId, String returnParkingLotName) {
        Rental rental = getRental(rentalId);
        if (rental.getStatus() != RentalStatus.IN_PROGRESS) {
            return rental;
        }
        Rental rentalToUpdate = new Rental();
        com.vincenzo.bikehub.entity.Bicycle rentedBicycleToUpdate = bicycleService.getBicycleEntity(rental.getBicycleId());
        if (returnParkingLotName != null) {
            rentalToUpdate.setReturnParkingLotName(returnParkingLotName);
            rentedBicycleToUpdate.setCurrentParkingLot(parkingLotService.getParkingLotEntity(returnParkingLotName));
        } else {
            String pickUpParkingLotName = rental.getPickUpParkingLotName();
            rentalToUpdate.setReturnParkingLotName(pickUpParkingLotName);
            rentedBicycleToUpdate.setCurrentParkingLot(parkingLotService.getParkingLotEntity(pickUpParkingLotName));
        }
        Instant rentalFinishedAt = Instant.now();
        rentalToUpdate.setFinishedAt(rentalFinishedAt);
        Instant rentalStartedAt = rental.getStartedAt();
        Float hourlyPrice = rentedBicycleToUpdate.getHourlyPrice();
        Float randomMileage = (float) (Math.random() * 1000);
        rentalToUpdate.setTotalPrice(determinateRentalTotalPrice(hourlyPrice, rentalStartedAt, rentalFinishedAt));
        rentalToUpdate.setStatus(RentalStatus.FINISHED);
        rentalToUpdate.setMileage(randomMileage);
        rentedBicycleToUpdate.setStatus(BicycleStatus.AVAILABLE);
        rentedBicycleToUpdate.setTotalRentTimeInSeconds(rentedBicycleToUpdate.getTotalRentTimeInSeconds() + Duration.between(rentalStartedAt, rentalFinishedAt).toSeconds());
        return updateRental(rentalId, rentalToUpdate);
    }

    public Rental payRental(UUID rentalId, com.vincenzo.bikehub.server.gen.model.PaymentType paymentType, String username) {
        Rental rental = getRental(rentalId);
        RentalStatus rentalStatus = rental.getStatus();
        if (rentalStatus == RentalStatus.PAYED) {
            log.error("Rental {} has already been paid for", rentalId);
            return rental;
        } else if (rentalStatus != RentalStatus.FINISHED) {
            log.error("Rental {} is not finished yet", rentalId);
            throw new RentalStatusException();
        }
        paymentService.processPayment(paymentType, rental.getTotalPrice(), username);
        rental.setStatus(RentalStatus.PAYED);
        return updateRental(rentalId, rental);
    }

    public RentalsPage retrieveRentals(Pageable paging, List<RentalStatus> statuses, String userUsername) {
        Page<com.vincenzo.bikehub.entity.Rental> rentalsEntitiesPage;

        if (userUsername != null && !statuses.isEmpty()) {
            rentalsEntitiesPage = rentalRepository.findByStatusInAndAccountUsername(paging, statuses, userUsername);
        } else if (userUsername != null) {
            rentalsEntitiesPage = rentalRepository.findByAccountUsername(paging, userUsername);
        } else if (!statuses.isEmpty()) {
            rentalsEntitiesPage = rentalRepository.findByStatusIn(paging, statuses);
        } else {
            rentalsEntitiesPage = rentalRepository.findAll(paging);
        }

        RentalsPage rentalsPage = new RentalsPage();
        List<Rental> rentals = rentalsEntitiesPage.getContent().stream().map(rentalMapper::entityToModel).toList();
        rentalsPage.setRentals(rentals);
        rentalsPage.setTotalPages(rentalsEntitiesPage.getTotalPages());
        rentalsPage.setCurrentPage(rentalsEntitiesPage.getNumber());
        rentalsPage.setTotalItems((int) rentalsEntitiesPage.getTotalElements());

        return rentalsPage;
    }

}
