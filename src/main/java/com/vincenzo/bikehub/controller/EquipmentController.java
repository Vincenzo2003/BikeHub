package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.mapper.EquipmentMapper;
import com.vincenzo.bikehub.server.gen.controller.EquipmentApi;
import com.vincenzo.bikehub.server.gen.model.*;
import com.vincenzo.bikehub.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class EquipmentController implements EquipmentApi {

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
    public ResponseEntity<CreateEquipment201Response> createEquipment(CreateEquipmentRequest createEquipmentRequest) {
        com.vincenzo.bikehub.models.Equipment serializedEquipmentModel = equipmentMapper.createEquipmentRequestToModel(createEquipmentRequest);
        com.vincenzo.bikehub.models.Equipment createdEquipmentModel = equipmentService.createEquipment(serializedEquipmentModel);
        CreateEquipment201Response response = equipmentMapper.modelToCreateEquipment201Response(createdEquipmentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteEquipment(UUID equipmentId) {
        return null;
    }

    @Override
    public ResponseEntity<RetrieveEquipmentResponse> retrieveEquipment(UUID equipmentId) {
        return null;
    }

    @Override
    public ResponseEntity<RetrieveEquipmentResponse> updateEquipment(UUID equipmentId, UpdateEquipmentRequest updateEquipmentRequest) {
        return null;
    }
}
