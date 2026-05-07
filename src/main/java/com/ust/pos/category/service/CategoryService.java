package com.ust.pos.category.service;

import com.ust.pos.dto.CategoryDto;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    boolean delete(String identifier);

    List<CategoryDto> findAll();

    CategoryDto findByIdentifier(String identifier);

    List<CategoryDto> findBySuperCategoryNotNull();

    CategoryDto toggleStatus(String identifier);

    List<CategoryDto> findIfTrue();

    List<CategoryDto> findAllActive();

}
