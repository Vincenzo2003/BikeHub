package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.enums.CategoryType;
import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.models.Category;
import com.vincenzo.bikehub.models.Equipment;
import com.vincenzo.bikehub.models.ParkingLot;
import com.vincenzo.bikehub.server.gen.model.*;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {BicycleStatus.class, CategoryType.class, EquipmentType.class}
)
public abstract class BicycleMapper {

    public abstract CreateBicycleResponse modelToCreateBicycleResponse(Bicycle bicycle);

    @Mapping(target = "categories", expression = "java(mapToBicycleCategoryType(bicycle.getCategories()))")
    @Mapping(target = "equipments", expression = "java(mapEquipmentsToUuids(bicycle.getEquipments()))")
    @Mapping(target = "registeredAt", expression = "java(mapInstantToOffsetDateTime(bicycle.getRegisteredAt()))")
    @Mapping(target = "currentParkingLotName", expression = "java(mapCurrentParkingLotName(bicycle.getCurrentParkingLot()))")
    public abstract RetrieveBicycleResponse modelToRetrieveBicycleResponse(Bicycle bicycle);

    protected List<BicycleCategoryType> mapToBicycleCategoryType(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return null;
        }

        return categories.stream()
                .map(category -> {
                    if (category.getType() == null) {
                        return null;
                    }
                    return BicycleCategoryType.valueOf(category.getType().name());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected List<UUID> mapEquipmentsToUuids(List<Equipment> equipments) {
        if (equipments == null || equipments.isEmpty()) {
            return null;
        }
        return equipments.stream()
            .map(Equipment::getId)
            .collect(Collectors.toList());
    }

   @Named("mapUuidToEquipments")
    protected List<Equipment> mapUuidToEquipments(List<UUID> equipments) {
        if (equipments == null || equipments.isEmpty()) {
            return null;
        }

        return equipments.stream()
                .map(id -> {
                    Equipment equipment = new Equipment();
                    equipment.setId(id);
                    return equipment;
                })
                .collect(Collectors.toList());
    }

    @Named("mapBicycleCategoryTypeToCategories")
    protected List<Category> mapBicycleCategoryTypeToCategories(List<BicycleCategoryType> categories) {
        if (categories == null || categories.isEmpty()) {
            return null;
        }

        return categories.stream()
                .map(categoryType -> {
                    try {
                        Category category = new Category();
                        category.setId(UUID.randomUUID());
                        category.setType(CategoryType.valueOf(categoryType.name()));
                        category.setDescription(categoryType.name());
                        return category;
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected OffsetDateTime mapInstantToOffsetDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }

        return instant.atOffset(ZoneOffset.UTC);
    }

    protected String mapCurrentParkingLotName(ParkingLot parkingLot) {
        return parkingLot.getName();
    }

    @Named("mapCurrentParkingLotNameToParkingLot")
    protected ParkingLot mapCurrentParkingLotNameToParkingLot(String parkingLotName) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(parkingLotName);
        return parkingLot;
    }

    public abstract Bicycle entityToModel(com.vincenzo.bikehub.entity.Bicycle bicycle);

    public abstract com.vincenzo.bikehub.entity.Bicycle modelToEntity(Bicycle bicycle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRentTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "categories", expression = "java(mapBicycleCategoryTypeToCategories(createBicycleRequest.getCategories()))")
    @Mapping(target = "equipments", expression = "java(mapUuidToEquipments(createBicycleRequest.getEquipments()))")
    @Mapping(target = "currentParkingLot", expression = "java(mapCurrentParkingLotNameToParkingLot(createBicycleRequest.getCurrentParkingLotName()))")
    public abstract Bicycle createBicycleRequestToModel(CreateBicycleRequest createBicycleRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromModel(Bicycle bicycleModel, @MappingTarget com.vincenzo.bikehub.entity.Bicycle bicycleEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRentTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "categories", expression = "java(mapBicycleCategoryTypeToCategories(updateBicycleRequest.getCategories()))")
    @Mapping(target = "equipments", expression = "java(mapUuidToEquipments(updateBicycleRequest.getEquipments()))")
    @Mapping(target = "currentParkingLot", expression = "java(mapCurrentParkingLotNameToParkingLot(updateBicycleRequest.getCurrentParkingLotName()))")
    public abstract Bicycle updateBicycleRequestToModel(UpdateBicycleRequest updateBicycleRequest);
}
