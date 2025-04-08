package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.exceptions.BicycleChassisIdAlreadyRegisteredException;
import com.vincenzo.bikehub.exceptions.BicycleDeletingException;
import com.vincenzo.bikehub.exceptions.BicycleNotFoundException;
import com.vincenzo.bikehub.exceptions.BicycleSavingException;
import com.vincenzo.bikehub.mapper.BicycleMapper;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.models.Category;
import com.vincenzo.bikehub.models.Equipment;
import com.vincenzo.bikehub.models.ParkingLot;
import com.vincenzo.bikehub.repository.BicycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BicycleService {

    private final BicycleMapper bicycleMapper;
    private final BicycleRepository bicycleRepository;
    private final ParkingLotService parkingLotService;
    private final CategoryService categoryService;
    private final EquipmentService equipmentService;

    @Autowired
    public BicycleService(
        BicycleMapper bicycleMapper,
        BicycleRepository bicycleRepository,
        ParkingLotService parkingLotService,
        CategoryService categoryService,
        EquipmentService equipmentService
    ) {
        this.bicycleMapper = bicycleMapper;
        this.bicycleRepository = bicycleRepository;
        this.parkingLotService = parkingLotService;
        this.categoryService = categoryService;
        this.equipmentService = equipmentService;
    }

    public Bicycle getBicycle(UUID bicycleId) {
        com.vincenzo.bikehub.entity.Bicycle bicycleEntity =
            bicycleRepository.findById(bicycleId)
                .orElseThrow(BicycleNotFoundException::new);
        return bicycleMapper.entityToModel(bicycleEntity);
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

    private void retrieveAndSetCurrentParkingLot(Bicycle bicycle) {
        String currentParkingLotName = bicycle.getCurrentParkingLotName();
        ParkingLot parkingLot = parkingLotService.getParkingLot(currentParkingLotName);
        bicycle.setCurrentParkingLotName(parkingLot.getName());
    }



    @Transactional
    public Bicycle createBicycle(Bicycle bicycle) {
        if (bicycleRepository.existsByChassisId(bicycle.getChassisId())) {
            throw new BicycleChassisIdAlreadyRegisteredException();
        }
        retrieveAndSetCurrentParkingLot(bicycle);
        com.vincenzo.bikehub.entity.Bicycle bicycleEntity = new com.vincenzo.bikehub.entity.Bicycle();
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
