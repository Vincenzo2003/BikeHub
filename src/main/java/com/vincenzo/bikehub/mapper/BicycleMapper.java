package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.enums.CategoryType;
import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.*;
import com.vincenzo.bikehub.server.gen.model.*;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {BicycleStatus.class, CategoryType.class, EquipmentType.class}
)
public abstract class BicycleMapper {

    public abstract CreateBicycleResponse modelToCreateBicycleResponse(Bicycle bicycle);

    @Mapping(target = "equipments", source = "equipmentsIds")
    @Mapping(target = "registeredAt", expression = "java(mapInstantToOffsetDateTime(bicycle.getRegisteredAt()))")
    public abstract RetrieveBicycleResponse modelToRetrieveBicycleResponse(Bicycle bicycle);

    protected OffsetDateTime mapInstantToOffsetDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }

        return instant.atOffset(ZoneOffset.UTC);
    }

    @Mapping(target = "currentParkingLotName", source = "currentParkingLot.name")
    @Mapping(target = "equipmentsIds", source = "equipmentsIds")
    @Mapping(target = "categories", source = "categoriesTypes")
    public abstract Bicycle entityToModel(com.vincenzo.bikehub.entity.Bicycle bicycle);

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
    public abstract Bicycle updateBicycleRequestToModel(UpdateBicycleRequest updateBicycleRequest);
}
