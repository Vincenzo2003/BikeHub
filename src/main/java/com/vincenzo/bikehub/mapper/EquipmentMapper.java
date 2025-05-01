package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.Equipment;
import com.vincenzo.bikehub.server.gen.model.CreateEquipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {EquipmentType.class},
        uses = {BicycleMapper.class}

        )
public abstract class EquipmentMapper {

    @Mapping(target = "bicycleId", source = "bicycle.id")
    public abstract Equipment entityToModel(com.vincenzo.bikehub.entity.Equipment equipment);

    @Mapping(target = "id", ignore = true)
    public abstract Equipment createEquipmentToModel(CreateEquipment createEquipmentRequest);

    public abstract com.vincenzo.bikehub.server.gen.model.Equipment modelToEquipment(Equipment equipment);
}
