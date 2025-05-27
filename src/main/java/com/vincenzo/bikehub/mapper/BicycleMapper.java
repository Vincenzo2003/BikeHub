package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.models.BicyclesPage;
import com.vincenzo.bikehub.server.gen.model.*;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {BicycleStatus.class, BicycleCategory.class, EquipmentType.class},
        uses = {IDateTimeMapper.class}
)
public abstract class BicycleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRentTimeInSeconds", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "equipmentsIds", ignore = true)
    @Mapping(target = "stats", ignore = true)
    public abstract Bicycle createBicycleRequestToModel(CreateBicycle createBicycleRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRentTimeInSeconds", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "equipmentsIds", ignore = true)
    @Mapping(target = "chassisId", ignore = true)
    @Mapping(target = "stats", ignore = true)
    public abstract Bicycle updateBicycleRequestToModel(UpdateBicycle updateBicycleRequest);

    @Mapping(target = "equipments", source = "bicycle.equipmentsIds")
    public abstract com.vincenzo.bikehub.server.gen.model.Bicycle modelToBicycle(Bicycle bicycle);

    @Mapping(target = "currentParkingLotName", source = "currentParkingLot.name")
    @Mapping(target = "stats", ignore = true)
    public abstract Bicycle entityToModel(com.vincenzo.bikehub.entity.Bicycle bicycle);

    @Mapping(target = "results", source = "bicycles")
    public abstract com.vincenzo.bikehub.server.gen.model.BicyclesPage bicyclesPageModelToBicyclesPage(BicyclesPage bicyclesPage);
}
