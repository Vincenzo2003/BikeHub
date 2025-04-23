package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.*;
import com.vincenzo.bikehub.server.gen.model.BicycleCategory;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {BicycleStatus.class, BicycleCategory.class, EquipmentType.class},
        uses = {BicycleMapper.class}
)
public abstract class ParkingLotMapper {

    @Mapping(target = "parkedBicyclesIds", source = "parkedBicyclesIds")
    public abstract ParkingLot entityToModel(com.vincenzo.bikehub.entity.ParkingLot parkingLot);

}
