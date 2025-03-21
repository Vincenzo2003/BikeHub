package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class PaymentMethod {
    private UUID id;

    private String cc;

    private String cvc;

    private String holder;

    private PaymentType type;

    private Date expire_at;

}
