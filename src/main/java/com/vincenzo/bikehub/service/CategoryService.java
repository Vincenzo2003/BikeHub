package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.CategoryNotFound;
import com.vincenzo.bikehub.enums.CategoryType;
import com.vincenzo.bikehub.mapper.CategoryMapper;
import com.vincenzo.bikehub.models.Category;
import com.vincenzo.bikehub.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryService(
        CategoryRepository categoryRepository,
        CategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Category getCategory(CategoryType type) {
        com.vincenzo.bikehub.entity.Category categoryEntity =
            categoryRepository.findByType(type)
                .orElseThrow(CategoryNotFound::new);
        return categoryMapper.entityToModel(categoryEntity);
    }

}
