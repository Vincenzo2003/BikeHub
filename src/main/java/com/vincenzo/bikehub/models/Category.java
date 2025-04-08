package com.vincenzo.bikehub.models;

import com.vincenzo.bikehub.enums.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class Category {

    private CategoryType type;

    private String description;

}
