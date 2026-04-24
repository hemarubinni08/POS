package com.ust.pos.category.service;

import com.ust.pos.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories();

    boolean deleteCategory(Long id);
}
