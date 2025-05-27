package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.mapper.EquipmentMapper;
import com.vincenzo.bikehub.models.Equipment;
import com.vincenzo.bikehub.server.gen.controller.EquipmentApi;
import com.vincenzo.bikehub.server.gen.controller.EquipmentsApi;
import com.vincenzo.bikehub.server.gen.model.*;
import com.vincenzo.bikehub.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class EquipmentController implements EquipmentApi, EquipmentsApi {

    private final EquipmentService equipmentService;
    private final EquipmentMapper equipmentMapper;

    @Autowired
    public EquipmentController(
        EquipmentService equipmentService,
        EquipmentMapper equipmentMapper
    ) {
        this.equipmentService = equipmentService;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Equipment> createEquipment(CreateEquipment createEquipmentRequest) {
        Equipment serializedEquipmentModel = equipmentMapper.createEquipmentToModel(createEquipmentRequest);
        Equipment createdEquipmentModel = equipmentService.createEquipment(serializedEquipmentModel);
        com.vincenzo.bikehub.server.gen.model.Equipment response = equipmentMapper.modelToEquipment(createdEquipmentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Equipment> retrieveEquipment(UUID equipmentId) {
        Equipment equipment = equipmentService.getEquipmentModel(equipmentId);
        com.vincenzo.bikehub.server.gen.model.Equipment response = equipmentMapper.modelToEquipment(equipment);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Equipment> updateEquipment(UUID equipmentId, UpdateEquipment updateEquipment) {
        Equipment serializedEquipmentModel = equipmentMapper.updateEquipmentToModel(updateEquipment);
        Equipment updatedEquipmentModel = equipmentService.updateEquipment(equipmentId, serializedEquipmentModel);
        com.vincenzo.bikehub.server.gen.model.Equipment response = equipmentMapper.modelToEquipment(updatedEquipmentModel);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEquipment(UUID equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<EquipmentsPage> retrieveEquipments(Integer page, Integer count) {
        Pageable paging = PageRequest.of(page, count);
        com.vincenzo.bikehub.models.EquipmentsPage equipmentsPage = equipmentService.retrieveEquipments(paging);
        com.vincenzo.bikehub.server.gen.model.EquipmentsPage response = equipmentMapper.equipmentsPageModelToEquipmentsPage(equipmentsPage);
        return ResponseEntity.ok(response);
    }
}
