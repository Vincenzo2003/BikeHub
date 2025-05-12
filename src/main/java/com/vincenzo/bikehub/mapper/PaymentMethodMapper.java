package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.models.PaymentMethod;
import com.vincenzo.bikehub.server.gen.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class PaymentMethodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    public abstract PaymentMethod createPaymentMethodToModel(CreatePaymentMethod createPaymentMethod);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    public abstract PaymentMethod updatePaymentMethodToModel(UpdatePaymentMethod updatePaymentMethod);

    public abstract com.vincenzo.bikehub.server.gen.model.PaymentMethod modelToPaymentMethod(PaymentMethod paymentMethod);

    @Mapping(target = "accountId", source = "account.id")
    public abstract PaymentMethod entityToModel(com.vincenzo.bikehub.entity.PaymentMethod paymentMethod);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    public abstract com.vincenzo.bikehub.entity.PaymentMethod modelToEntity(PaymentMethod paymentMethod);

}
