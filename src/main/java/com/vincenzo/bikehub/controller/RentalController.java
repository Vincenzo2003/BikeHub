package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.server.gen.controller.RentalApi;
import com.vincenzo.bikehub.server.gen.model.CreateEquipment201Response;
import com.vincenzo.bikehub.server.gen.model.CreateRentalRequest;
import com.vincenzo.bikehub.server.gen.model.RetrieveRentalResponse;
import com.vincenzo.bikehub.server.gen.model.UpdateRentalRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class RentalController implements RentalApi {
    @Override
    public ResponseEntity<CreateEquipment201Response> createRental(CreateRentalRequest createRentalRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRental(UUID rentalId) {
        return null;
    }

    @Override
    public ResponseEntity<RetrieveRentalResponse> retrieveRental(UUID rentalId) {
        return null;
    }

    @Override
    public ResponseEntity<RetrieveRentalResponse> updateRental(UUID rentalId, UpdateRentalRequest updateRentalRequest) {
        return null;
    }
}
