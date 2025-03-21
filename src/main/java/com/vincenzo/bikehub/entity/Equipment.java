package com.vincenzo.bikehub.entity;

import com.vincenzo.bikehub.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Bicycle bicycle;

    @Column(nullable = false)
    private EquipmentType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private String imageUrl;
}
