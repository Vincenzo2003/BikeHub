package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.exceptions.BicycleChassisIdAlreadyRegisteredException;
import com.vincenzo.bikehub.exceptions.BicycleDeletingException;
import com.vincenzo.bikehub.exceptions.BicycleNotFoundException;
import com.vincenzo.bikehub.exceptions.BicycleSavingException;
import com.vincenzo.bikehub.mapper.BicycleMapper;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.repository.BicycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BicycleService {

    private final BicycleMapper bicycleMapper;
    private final BicycleRepository bicycleRepository;
    private final ParkingLotService parkingLotService;

    @Autowired
    public BicycleService(
        BicycleMapper bicycleMapper,
        BicycleRepository bicycleRepository,
        ParkingLotService parkingLotService
    ) {
        this.bicycleMapper = bicycleMapper;
        this.bicycleRepository = bicycleRepository;
        this.parkingLotService = parkingLotService;
    }

    public Bicycle getBicycleModel(UUID bicycleId) {
        com.vincenzo.bikehub.entity.Bicycle bicycleEntity =
            bicycleRepository.findById(bicycleId)
                .orElseThrow(BicycleNotFoundException::new);
        return bicycleMapper.entityToModel(bicycleEntity);
    }

    public com.vincenzo.bikehub.entity.Bicycle getBicycleEntity(UUID bicycleId) {
        return bicycleRepository.findById(bicycleId)
                .orElseThrow(BicycleNotFoundException::new);
    }

    @Transactional
    public void deleteBicycle(UUID bicycleId) {
        com.vincenzo.bikehub.entity.Bicycle existingBicycle =
            bicycleRepository.findByIdWithPessimisticLock(bicycleId)
                .orElseThrow(BicycleNotFoundException::new);

        if (existingBicycle.getStatus() == BicycleStatus.RENTED) {
            throw new IllegalStateException("Cannot delete a rented bicycle");
        }

        try {
            bicycleRepository.delete(existingBicycle);
        } catch (DataIntegrityViolationException exc) {
            throw new BicycleDeletingException();
        }
    }

    @Transactional
    public Bicycle createBicycle(Bicycle bicycle) {
        if (bicycleRepository.existsByChassisId(bicycle.getChassisId())) {
            throw new BicycleChassisIdAlreadyRegisteredException();
        }
        com.vincenzo.bikehub.entity.Bicycle bicycleEntity = new com.vincenzo.bikehub.entity.Bicycle();
        bicycleEntity.setCurrentParkingLot(parkingLotService.getParkingLotEntity(bicycle.getCurrentParkingLotName()));
        bicycleEntity.setCategories(bicycle.getCategories());
        bicycleEntity.setChassisId(bicycle.getChassisId());
        bicycleEntity.setStatus(bicycle.getStatus());
        bicycleEntity.setBrand(bicycle.getBrand());
        bicycleEntity.setModel(bicycle.getModel());
        bicycleEntity.setTotalRentTime(bicycle.getTotalRentTime());
        bicycleEntity.setHourlyPrice(bicycle.getHourlyPrice());
    try {
            bicycleEntity = bicycleRepository.saveAndFlush(bicycleEntity);
        } catch (DataIntegrityViolationException exc){
            throw new BicycleSavingException();
        }
        return bicycleMapper.entityToModel(bicycleEntity);
    }

    @Transactional
    public Bicycle updateBicycle(UUID bicycleId, Bicycle bicycle) {
        com.vincenzo.bikehub.entity.Bicycle existingBicycle =
                bicycleRepository.findByIdWithPessimisticLock(bicycleId)
                        .orElseThrow(BicycleNotFoundException::new);

        if (existingBicycle.getStatus() == BicycleStatus.RENTED) {
            throw new IllegalStateException("Cannot update a rented bicycle");
        }

//        bicycleMapper.updateEntityFromModel(bicycle, existingBicycle);
        com.vincenzo.bikehub.entity.Bicycle savedBicycle;
        try {
            savedBicycle = bicycleRepository.saveAndFlush(existingBicycle);
        } catch (DataIntegrityViolationException exc) {
            throw new BicycleSavingException();
        }

        return bicycleMapper.entityToModel(savedBicycle);
    }
}
