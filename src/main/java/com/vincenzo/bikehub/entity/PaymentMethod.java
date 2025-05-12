package com.vincenzo.bikehub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column
    private String cc;

    @Column
    private String cvc;

    @Column
    private String holder;

    @Column(nullable = false)
    private com.vincenzo.bikehub.server.gen.model.PaymentType type;

    @Column
    private Date expireAt;

    @ManyToOne
    private Account account;

}
