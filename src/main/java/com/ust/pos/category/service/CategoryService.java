package com.ust.pos.category.service;

import com.ust.pos.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto userDto);
    CategoryDto update(CategoryDto userDto);
    void delete(String identifier);
    List<CategoryDto> findAll();
    CategoryDto findByIdentifier(String identifier);
    List<CategoryDto> findBySuperCategoryNotNull();
}
