package com.ust.pos.category.service;

import com.ust.pos.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(String identifier);

    List<CategoryDto> findAll();

    List<CategoryDto> findAllWithoutNull();


    CategoryDto findByIdentifier(String identifier);
}
