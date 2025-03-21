package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.BicycleStatus;
import com.vincenzo.bikehub.enums.CategoryType;
import com.vincenzo.bikehub.enums.EquipmentType;
import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.server.gen.model.CreateBicycleRequest;
import com.vincenzo.bikehub.server.gen.model.CreateBicycleResponse;
import com.vincenzo.bikehub.server.gen.model.RetrieveBicycleResponse;
import com.vincenzo.bikehub.server.gen.model.UpdateBicycleRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {BicycleStatus.class, CategoryType.class, EquipmentType.class}
)
public abstract class BicycleMapper {

    public abstract CreateBicycleResponse modelToCreateBicycleResponse(Bicycle bicycle);

    public abstract RetrieveBicycleResponse modelToRetrieveBicycleResponse(Bicycle bicycle);

    public abstract Bicycle entityToModel(com.vincenzo.bikehub.entity.Bicycle bicycle);

    public abstract com.vincenzo.bikehub.entity.Bicycle modelToEntity(Bicycle bicycle);

    public abstract Bicycle createBicycleRequestToModel(CreateBicycleRequest createBicycleRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromModel(Bicycle bicycleModel, @MappingTarget com.vincenzo.bikehub.entity.Bicycle bicycleEntity);

    public abstract Bicycle updateBicycleRequestToModel(UpdateBicycleRequest updateBicycleRequest);
}
