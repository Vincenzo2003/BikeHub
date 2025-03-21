package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.enums.AccountRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class Account
{
    private UUID id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;

    private AccountRole role;

    private List<PaymentMethod> paymentMethods;

    private List<Rental> rentals;

}
