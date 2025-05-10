package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.mapper.BicycleMapper;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.server.gen.controller.BicycleApi;
import com.vincenzo.bikehub.server.gen.model.*;
import com.vincenzo.bikehub.service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BicycleController implements BicycleApi {

    private final BicycleService bicycleService;
    private final BicycleMapper bicycleMapper;

    @Autowired
    public BicycleController(
        BicycleService bicycleService,
        BicycleMapper bicycleMapper
    ) {
        this.bicycleService = bicycleService;
        this.bicycleMapper = bicycleMapper;
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Bicycle> createBicycle(CreateBicycle createBicycleRequest) {
        Bicycle serializedBicycleModel = bicycleMapper.createBicycleRequestToModel(createBicycleRequest);
        Bicycle createdBicycleModel = bicycleService.createBicycle(serializedBicycleModel);
        com.vincenzo.bikehub.server.gen.model.Bicycle response = bicycleMapper.modelToBicycle(createdBicycleModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBicycle(UUID bicycleId) {
        bicycleService.deleteBicycle(bicycleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Bicycle> retrieveBicycle(UUID bicycleId) {
        Bicycle bicycle = bicycleService.getBicycleModel(bicycleId);
        com.vincenzo.bikehub.server.gen.model.Bicycle response = bicycleMapper.modelToBicycle(bicycle);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<com.vincenzo.bikehub.server.gen.model.Bicycle> updateBicycle(UUID bicycleId, UpdateBicycle updateBicycleRequest) {
        Bicycle serializedBicycleModel = bicycleMapper.updateBicycleRequestToModel(updateBicycleRequest);
        Bicycle bicycle = bicycleService.updateBicycle(bicycleId, serializedBicycleModel);
        com.vincenzo.bikehub.server.gen.model.Bicycle response = bicycleMapper.modelToBicycle(bicycle);
        return ResponseEntity.ok(response);
    }
}
