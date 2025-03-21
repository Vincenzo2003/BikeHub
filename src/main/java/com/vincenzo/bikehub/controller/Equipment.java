package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.server.gen.controller.EquipmentApi;
import com.vincenzo.bikehub.server.gen.model.CreateEquipment201Response;
import com.vincenzo.bikehub.server.gen.model.CreateEquipmentRequest;
import com.vincenzo.bikehub.server.gen.model.RetrieveEquipmentResponse;
import com.vincenzo.bikehub.server.gen.model.UpdateEquipmentRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class Equipment implements EquipmentApi {
    @Override
    public ResponseEntity<CreateEquipment201Response> createEquipment(CreateEquipmentRequest createEquipmentRequest) {
        return null;
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
