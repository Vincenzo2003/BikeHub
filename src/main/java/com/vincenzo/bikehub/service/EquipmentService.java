package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.BicycleSavingException;
import com.vincenzo.bikehub.exceptions.EquipmentNotFound;
import com.vincenzo.bikehub.mapper.EquipmentMapper;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.models.Equipment;
import com.vincenzo.bikehub.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;
    private final BicycleService bicycleService;


    @Autowired
    public EquipmentService(
            EquipmentRepository equipmentRepository,
            EquipmentMapper equipmentMapper, BicycleService bicycleService
    ) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
        this.bicycleService = bicycleService;
    }

    public Equipment getEquipment(UUID equipmentId) {
        com.vincenzo.bikehub.entity.Equipment EquipmentEntity =
                equipmentRepository.findById(equipmentId)
                        .orElseThrow(EquipmentNotFound::new);
        return equipmentMapper.entityToModel(EquipmentEntity);
    }

    @Transactional
    public Equipment createEquipment(Equipment equipment) {
        com.vincenzo.bikehub.entity.Equipment equipmentEntity = new com.vincenzo.bikehub.entity.Equipment();
        equipmentEntity.setName(equipment.getName());
        equipmentEntity.setBicycle(bicycleService.getBicycleEntity(equipment.getBicycleId()));
        equipmentEntity.setType(equipment.getType());
        equipmentEntity.setDescription(equipment.getDescription());
        equipmentEntity.setImageUrl(equipment.getImageUrl());
    try {
            equipmentEntity = equipmentRepository.saveAndFlush(equipmentEntity);
        } catch (DataIntegrityViolationException exc){
            throw new BicycleSavingException();
        }
        return equipmentMapper.entityToModel(equipmentEntity);
    }

    public com.vincenzo.bikehub.entity.Equipment getEquipmentEntity(UUID equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(EquipmentNotFound::new);
    }

    public Equipment getEquipmentModel(UUID equipmentId) {
        com.vincenzo.bikehub.entity.Equipment existingEquipment = getEquipmentEntity(equipmentId);
        return equipmentMapper.entityToModel(existingEquipment);
    }

    @Transactional
    public Equipment updateEquipment(UUID equipmentId, Equipment equipment) {
        com.vincenzo.bikehub.entity.Equipment existingEquipment = getEquipmentEntity(equipmentId);
        if (equipment.getBicycleId() != null) {
            existingEquipment.setBicycle(bicycleService.getBicycleEntity(equipment.getBicycleId()));
        }
        if (equipment.getName() != null) {
            existingEquipment.setName(equipment.getName());
        }
        if (equipment.getType() != null) {
            existingEquipment.setType(equipment.getType());
        }
        if (equipment.getDescription() != null) {
            existingEquipment.setDescription(equipment.getDescription());

        }
        if (equipment.getImageUrl() != null) {
            existingEquipment.setImageUrl(equipment.getImageUrl());
        }
        com.vincenzo.bikehub.entity.Equipment savedEquipment;
        try {
            savedEquipment = equipmentRepository.saveAndFlush(existingEquipment);
        } catch (DataIntegrityViolationException exc) {
            throw new BicycleSavingException();
        }
        return equipmentMapper.entityToModel(savedEquipment);
    }

    @Transactional
    public void deleteEquipment(UUID equipmentId) {
        com.vincenzo.bikehub.entity.Equipment existingEquipment = getEquipmentEntity(equipmentId);
        try {
            equipmentRepository.delete(existingEquipment);
        } catch (DataIntegrityViolationException exc) {
            throw new BicycleSavingException();
        }
    }
}
