package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.server.gen.model.*;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {BicycleStatus.class, BicycleCategory.class, EquipmentType.class}
)
public abstract class BicycleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRentTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "equipmentsIds", ignore = true)
    public abstract Bicycle createBicycleRequestToModel(CreateBicycleRequest createBicycleRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRentTime", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "equipmentsIds", ignore = true)
    @Mapping(target = "chassisId", ignore = true)
    public abstract Bicycle updateBicycleRequestToModel(UpdateBicycleRequest updateBicycleRequest);

    public abstract CreateBicycleResponse modelToCreateBicycleResponse(Bicycle bicycle);

    protected OffsetDateTime mapInstantToOffsetDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }

        return instant.atOffset(ZoneOffset.UTC);
    }

    @Mapping(target = "equipments", source = "bicycle.equipmentsIds")
    @Mapping(target = "registeredAt", expression = "java(mapInstantToOffsetDateTime(bicycle.getRegisteredAt()))")
    public abstract RetrieveBicycleResponse modelToRetrieveBicycleResponse(Bicycle bicycle);

    @Mapping(target = "currentParkingLotName", ignore = true)
    public abstract Bicycle entityToModel(com.vincenzo.bikehub.entity.Bicycle bicycle);

    @Mapping(target = "currentParkingLot", ignore = true)
    @Mapping(target = "equipments", ignore = true)
    public abstract com.vincenzo.bikehub.entity.Bicycle modelToEntity(Bicycle bicycle);

}
