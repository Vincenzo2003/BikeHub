package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.server.gen.model.CreateRental;
import com.vincenzo.bikehub.server.gen.model.RentalStatus;
import com.vincenzo.bikehub.server.gen.model.ReturnRentalDetails;
import com.vincenzo.bikehub.server.gen.model.UpdateRental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {RentalStatus.class},
        uses = {IDateTimeMapper.class}
)
public abstract class RentalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pickUpParkingLotName", ignore = true)
    @Mapping(target = "returnParkingLotName", ignore = true)
    @Mapping(target = "paymentMethodId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "mileage", ignore = true)
    public abstract Rental createRentalToModel(CreateRental createRentalRequest);

    @Mapping(target = "bicycleId", source = "bicycle.id")
    @Mapping(target = "pickUpParkingLotName", source = "pickUpParkingLot.name")
    @Mapping(target = "returnParkingLotName", source = "returnParkingLot.name")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    public abstract Rental entityToModel(com.vincenzo.bikehub.entity.Rental rental);

    public abstract com.vincenzo.bikehub.server.gen.model.Rental modelToRental(Rental rental);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bicycleId", ignore = true)
    @Mapping(target = "pickUpParkingLotName", ignore = true)
    @Mapping(target = "paymentMethodId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    public abstract Rental updateRentalRequestToModel(UpdateRental updateRentalRequest);
}
