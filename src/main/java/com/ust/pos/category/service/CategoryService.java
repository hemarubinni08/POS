package com.ust.pos.category.service;

import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.WsDto;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    WsDto<CategoryDto> findAll(Pageable pageable);

    CategoryDto findByIdentifier(String categoryCode);

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(String identifier);

    WsDto<CategoryDto> findAllCategoriesWithNoSuper();
}
