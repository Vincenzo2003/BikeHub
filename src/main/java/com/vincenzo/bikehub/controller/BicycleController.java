package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.mapper.BicycleMapper;
import com.vincenzo.bikehub.server.gen.controller.BicycleApi;
import com.vincenzo.bikehub.server.gen.model.CreateBicycleRequest;
import com.vincenzo.bikehub.server.gen.model.CreateBicycleResponse;
import com.vincenzo.bikehub.server.gen.model.RetrieveBicycleResponse;
import com.vincenzo.bikehub.server.gen.model.UpdateBicycleRequest;
import com.vincenzo.bikehub.service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CreateBicycleResponse> createBicycle(CreateBicycleRequest createBicycleRequest) {
        com.vincenzo.bikehub.models.Bicycle serializedBicycleModel = bicycleMapper.createBicycleRequestToModel(createBicycleRequest);
        com.vincenzo.bikehub.models.Bicycle createdBicycleModel = bicycleService.createBicycle(serializedBicycleModel);
        CreateBicycleResponse response = bicycleMapper.modelToCreateBicycleResponse(createdBicycleModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteBicycle(UUID bicycleId) {
        bicycleService.deleteBicycle(bicycleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<RetrieveBicycleResponse> retrieveBicycle(UUID bicycleId) {
        com.vincenzo.bikehub.models.Bicycle bicycle = bicycleService.getBicycleModel(bicycleId);
        RetrieveBicycleResponse response = bicycleMapper.modelToRetrieveBicycleResponse(bicycle);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RetrieveBicycleResponse> updateBicycle(UUID bicycleId, UpdateBicycleRequest updateBicycleRequest) {
        return null;
    }

//    @Override
//    public ResponseEntity<RetrieveBicycleResponse> updateBicycle(UUID bicycleId, UpdateBicycleRequest updateBicycleRequest) {
//        com.vincenzo.bikehub.models.Bicycle serializedBicycleModel = bicycleMapper.updateBicycleRequestToModel(updateBicycleRequest);
//        com.vincenzo.bikehub.models.Bicycle bicycle = bicycleService.updateBicycle(bicycleId, serializedBicycleModel);
//        RetrieveBicycleResponse response = bicycleMapper.modelToRetrieveBicycleResponse(bicycle);
//        return ResponseEntity.ok(response);
//    }
}
