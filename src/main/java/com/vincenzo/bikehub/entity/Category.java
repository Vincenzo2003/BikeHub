package com.vincenzo.bikehub.entity;

import com.vincenzo.bikehub.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @Column(nullable = false, unique = true)
    private CategoryType type;

    @Column(nullable = false)
    private String description;
}
