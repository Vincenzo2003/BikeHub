package com.vincenzo.bikehub.mapper;

import com.vincenzo.bikehub.enums.AccountRole;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.models.PaymentMethod;
import com.vincenzo.bikehub.models.Rental;
import com.vincenzo.bikehub.server.gen.model.Login;
import com.vincenzo.bikehub.server.gen.model.SignUp;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {AccountRole.class}
)
public abstract class AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "paymentMethods", ignore = true)
    public abstract Account signUpRequestToModel(SignUp AccountSignUpData);

    @Mapping(target = "paymentMethods", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    public abstract com.vincenzo.bikehub.entity.Account modelToEntity(Account Account);

    @AfterMapping
    public void completeModelToEntity(
            Account AccountToMap,
            @MappingTarget com.vincenzo.bikehub.entity.Account mappedAccount
    ) {
        List<PaymentMethod> paymentMethodsToMap = AccountToMap.getPaymentMethods();
        if (paymentMethodsToMap != null) {
            ArrayList<com.vincenzo.bikehub.entity.PaymentMethod> mappedPaymentMethods = new ArrayList<> (paymentMethodsToMap.size());
            for (PaymentMethod paymentMethod : paymentMethodsToMap) {
                com.vincenzo.bikehub.entity.PaymentMethod createdPaymentMethod = new com.vincenzo.bikehub.entity.PaymentMethod();

                createdPaymentMethod.setId(paymentMethod.getId());
                createdPaymentMethod.setCc(paymentMethod.getCc());
                createdPaymentMethod.setCvc(paymentMethod.getCvc());
                createdPaymentMethod.setHolder(paymentMethod.getHolder());
                createdPaymentMethod.setType(paymentMethod.getType());
                mappedPaymentMethods.add(createdPaymentMethod);
            }
            mappedAccount.setPaymentMethods(mappedPaymentMethods);
        }

        List<Rental> rentalsToMap = AccountToMap.getRentals();
        if (rentalsToMap != null) {
            ArrayList<com.vincenzo.bikehub.entity.Rental> mappedRentals = new ArrayList<> (rentalsToMap.size());
            for (Rental rental : rentalsToMap) {
                com.vincenzo.bikehub.entity.Rental createdRental = new com.vincenzo.bikehub.entity.Rental();

                createdRental.setId(rental.getId());
                createdRental.setCreatedAt(rental.getCreatedAt());
                createdRental.setStartedAt(rental.getStartedAt());
                createdRental.setFinishedAt(rental.getFinishedAt());
                createdRental.setTotalPrice(rental.getTotalPrice());
                createdRental.setStatus(rental.getStatus());
                mappedRentals.add(createdRental);
            }
            mappedAccount.setRentals(mappedRentals);
        }
    }


    @Mapping(target = "paymentMethods", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    public abstract Account entityToModel (com.vincenzo.bikehub.entity.Account Account);

    @AfterMapping
    public void completeEntityToModel(
            com.vincenzo.bikehub.entity.Account AccountToMap,
            @MappingTarget Account mappedAccount
    ) {
        List<com.vincenzo.bikehub.entity.PaymentMethod> paymentMethodsToMap = AccountToMap.getPaymentMethods();
        if (paymentMethodsToMap != null) {
            ArrayList<PaymentMethod> mappedPaymentMethods = new ArrayList<> (paymentMethodsToMap.size());
            for (com.vincenzo.bikehub.entity.PaymentMethod paymentMethod : paymentMethodsToMap) {
                PaymentMethod createdPaymentMethod = new PaymentMethod();

                createdPaymentMethod.setId(paymentMethod.getId());
                createdPaymentMethod.setCc(paymentMethod.getCc());
                createdPaymentMethod.setCvc(paymentMethod.getCvc());
                createdPaymentMethod.setHolder(paymentMethod.getHolder());
                createdPaymentMethod.setType(paymentMethod.getType());
                mappedPaymentMethods.add(createdPaymentMethod);
            }
            mappedAccount.setPaymentMethods(mappedPaymentMethods);
        }

        List<com.vincenzo.bikehub.entity.Rental> rentalsToMap = AccountToMap.getRentals();
        if (rentalsToMap != null) {
            ArrayList<Rental> mappedRentals = new ArrayList<> (rentalsToMap.size());
            for (com.vincenzo.bikehub.entity.Rental rental : rentalsToMap) {
                Rental createdRental = new Rental();

                createdRental.setId(rental.getId());
                createdRental.setCreatedAt(rental.getCreatedAt());
                createdRental.setStartedAt(rental.getStartedAt());
                createdRental.setFinishedAt(rental.getFinishedAt());
                createdRental.setTotalPrice(rental.getTotalPrice());
                createdRental.setStatus(rental.getStatus());
                mappedRentals.add(createdRental);
            }
            mappedAccount.setRentals(mappedRentals);
        }
    }
}
