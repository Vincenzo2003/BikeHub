package com.vincenzo.bikehub.service;


import com.vincenzo.bikehub.exceptions.ParkingLotNotFoundException;
import com.vincenzo.bikehub.mapper.ParkingLotMapper;
import com.vincenzo.bikehub.models.ParkingLot;
import com.vincenzo.bikehub.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;

    @Autowired
    public ParkingLotService(
        ParkingLotRepository parkingLotRepository,
        ParkingLotMapper parkingLotMapper
    ) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
    }

    public ParkingLot getParkingLot(String name) {
        com.vincenzo.bikehub.entity.ParkingLot parkingLotEntity =
                parkingLotRepository.findByName(name)
                        .orElseThrow(ParkingLotNotFoundException::new);
        return parkingLotMapper.entityToModel(parkingLotEntity);
    }

}
