package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.BicycleChassisIdAlreadyRegisteredException;
import com.vincenzo.bikehub.exceptions.BicycleDeletingException;
import com.vincenzo.bikehub.exceptions.BicycleNotFoundException;
import com.vincenzo.bikehub.exceptions.BicycleSavingException;
import com.vincenzo.bikehub.mapper.BicycleMapper;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.repository.BicycleRepository;
import com.vincenzo.bikehub.server.gen.model.BicycleCategory;
import com.vincenzo.bikehub.server.gen.model.BicycleStatus;
import com.vincenzo.bikehub.server.gen.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
        com.vincenzo.bikehub.entity.Bicycle bicycleEntity = getBicycleEntity(bicycleId);
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
        bicycleEntity.setStatus(BicycleStatus.AVAILABLE);
        bicycleEntity.setBrand(bicycle.getBrand());
        bicycleEntity.setModel(bicycle.getModel());
        bicycleEntity.setTotalRentTimeInSeconds(bicycle.getTotalRentTimeInSeconds());
        bicycleEntity.setHourlyPrice(bicycle.getHourlyPrice());
    try {
            bicycleEntity = bicycleRepository.saveAndFlush(bicycleEntity);
        } catch (DataIntegrityViolationException exc){
            throw new BicycleSavingException();
        }
        return bicycleMapper.entityToModel(bicycleEntity);
    }

    public Integer calculateUsagePercentage(Long bicycleTotalRentTime) {
        Long totalRentTime = bicycleRepository.getTotalRentTime();
        if (totalRentTime == null || totalRentTime.equals(0L)) {
            return 0;
        }
        Long percentage = (bicycleTotalRentTime * 100) / totalRentTime;
        return  percentage.intValue();
    };

    public Bicycle retrieveBicycle(UUID bicycleId, Boolean withStats) {
        Bicycle bicycleModel = getBicycleModel(bicycleId);
        if (withStats != null && withStats) {
            Stats stats = new Stats();
            stats.setUsagePercentage(calculateUsagePercentage(bicycleModel.getTotalRentTimeInSeconds()));
            bicycleModel.setStats(stats);
        }
        return bicycleModel;
    }

    @Transactional
    public Bicycle updateBicycle(UUID bicycleId, Bicycle bicycle) {
        com.vincenzo.bikehub.entity.Bicycle existingBicycle =
            getBicycleEntity(bicycleId);

        if (existingBicycle.getStatus() == BicycleStatus.RENTED) {
            throw new IllegalStateException("Cannot update a rented bicycle");
        }

        if (bicycle.getCurrentParkingLotName() != null) {
            existingBicycle.setCurrentParkingLot(parkingLotService.getParkingLotEntity(bicycle.getCurrentParkingLotName()));
        }
        if (bicycle.getCategories() != null) {
            existingBicycle.setCategories(bicycle.getCategories());
        }
        if (bicycle.getBrand() != null) {
            existingBicycle.setBrand(bicycle.getBrand());
        }
        if (bicycle.getModel() != null) {
            existingBicycle.setModel(bicycle.getModel());
        }
        if (bicycle.getHourlyPrice() != null) {
            existingBicycle.setHourlyPrice(bicycle.getHourlyPrice());
        }
        if (bicycle.getStatus() != null) {
            existingBicycle.setStatus(bicycle.getStatus());
        }

        com.vincenzo.bikehub.entity.Bicycle savedBicycle;
        try {
            savedBicycle = bicycleRepository.saveAndFlush(existingBicycle);
        } catch (DataIntegrityViolationException exc) {
            throw new BicycleSavingException();
        }

        return bicycleMapper.entityToModel(savedBicycle);
    }

    @Transactional
    public void setBicycleStatus(UUID bicycleId, BicycleStatus status) {
        com.vincenzo.bikehub.entity.Bicycle existingBicycle =
            getBicycleEntity(bicycleId);
        if (status == BicycleStatus.RENTED){
            existingBicycle.setCurrentParkingLot(null);
        }
        existingBicycle.setStatus(status);
        try {
            bicycleRepository.saveAndFlush(existingBicycle);
        } catch (DataIntegrityViolationException exc) {
            throw new BicycleSavingException();
        }
    }

    public Stats retrieveCategoryStats(BicycleCategory category) {
        Long totalRentTime = bicycleRepository.getTotalRentTime();
        Stats stats = new Stats();
        if (totalRentTime == null || totalRentTime.equals(0L)) {
            return stats;
        }
        Long totalRentTimeByCategory = bicycleRepository.getTotalRentTimeByCategory(category);
        Long categoryUsagePercentage = (totalRentTimeByCategory / totalRentTime) * 100;
        stats.setUsagePercentage(categoryUsagePercentage.intValue());
        return stats;
    }
}
