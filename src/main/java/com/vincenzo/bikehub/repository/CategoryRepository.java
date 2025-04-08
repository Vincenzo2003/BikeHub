package com.vincenzo.bikehub.repository;

import com.vincenzo.bikehub.entity.Category;
import com.vincenzo.bikehub.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByType(CategoryType type);

}
