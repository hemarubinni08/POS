package com.ust.pos.category.service;

import com.ust.pos.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto);
    CategoryDto findByIdentifier(String identifier);
    List<CategoryDto> findAll();
    List<CategoryDto> findSuperCategories();
    List<CategoryDto> findLeafCategories();
    List<CategoryDto> findChildCategories();
    void delete(String identifier);
}