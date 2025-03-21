package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.entity.Bicycle;
import com.vincenzo.bikehub.enums.EquipmentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class Equipment {

    private UUID id;

    private Bicycle bicycle;

    private EquipmentType type;

    private String name;

    private String description;

    private String imageUrl;
}
