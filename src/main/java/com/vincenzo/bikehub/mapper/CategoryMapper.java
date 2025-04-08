package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.CategoryType;
import com.vincenzo.bikehub.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {CategoryType.class},
        uses = {BicycleMapper.class}

)
public abstract class CategoryMapper {

    public abstract Category entityToModel(com.vincenzo.bikehub.entity.Category category);

    public abstract com.vincenzo.bikehub.entity.Category modelToEntity(Category category);

}
