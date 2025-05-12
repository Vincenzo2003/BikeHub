package com.vincenzo.bikehub.models;

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

    private com.vincenzo.bikehub.server.gen.model.PaymentType type;

    private Date expireAt;

    private UUID accountId;

}
