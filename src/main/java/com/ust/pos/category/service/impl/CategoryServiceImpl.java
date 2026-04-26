package com.ust.pos.category.service.impl;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;

import com.ust.pos.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // ================= FIND ALL =================

    @Override
    public List<CategoryDto> findAll() {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> dtoList = new ArrayList<>();

        for (Category category : categoryList) {
            dtoList.add(mapToDto(category));
        }

        return dtoList;
    }

    // ================= FIND SUPER CATEGORIES =================

    @Override
    public List<CategoryDto> findSuperCategories() {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> superList = new ArrayList<>();

        for (Category category : categoryList) {

            if (category.getSuperCategoryIdentifier() == null
                    || category.getSuperCategoryIdentifier().isEmpty()) {

                superList.add(mapToDto(category));
            }
        }

        return superList;
    }

    // ================= FIND LEAF CATEGORIES =================

    @Override
    public List<CategoryDto> findLeafCategories() {

        List<Category> allCategories = categoryRepository.findAll();
        List<CategoryDto> leafList = new ArrayList<>();

        for (Category category : allCategories) {

            boolean isParent = false;

            for (Category other : allCategories) {
                if (category.getIdentifier().equals(other.getSuperCategoryIdentifier())) {
                    isParent = true;
                    break;
                }
            }

            //  only categories that are NOT parent
            if (!isParent) {
                leafList.add(mapToDto(category));
            }
        }

        return leafList;
    }

    // ================= FIND BY IDENTIFIER =================

    @Override
    public CategoryDto findByIdentifier(String identifier) {

        CategoryDto dto = new CategoryDto();

        Category category = categoryRepository
                .findByIdentifier(identifier)
                .orElse(null);

        if (category == null) {
            dto.setSuccess(false);
            dto.setMessage("Category not found");
            return dto;
        }

        return mapToDto(category);
    }

    // ================= SAVE =================

    @Override
    public CategoryDto save(CategoryDto dto) {

        CategoryDto response = new CategoryDto();

        // duplicate check
        if (categoryRepository.existsByIdentifier(dto.getIdentifier())) {
            response.setSuccess(false);
            response.setMessage("Identifier already exists");
            return response;
        }

        // validation
        if (dto.getSuperCategoryIdentifier() != null
                && !dto.getSuperCategoryIdentifier().isEmpty()) {

            // self-parent check
            if (dto.getIdentifier().equals(dto.getSuperCategoryIdentifier())) {
                response.setSuccess(false);
                response.setMessage("Category cannot be its own parent");
                return response;
            }

            boolean exists = categoryRepository
                    .existsByIdentifier(dto.getSuperCategoryIdentifier());

            if (!exists) {
                response.setSuccess(false);
                response.setMessage("Invalid super category");
                return response;
            }
        }

        Category category = new Category();
        mapToEntity(dto, category);

        categoryRepository.save(category);

        response.setSuccess(true);
        return response;
    }

    // ================= UPDATE =================

    @Override
    public CategoryDto update(CategoryDto dto) {

        CategoryDto response = new CategoryDto();

        Category category = categoryRepository
                .findByIdentifier(dto.getIdentifier())
                .orElse(null);

        if (category == null) {
            response.setSuccess(false);
            response.setMessage("Category not found");
            return response;
        }

        // validation
        if (dto.getSuperCategoryIdentifier() != null
                && !dto.getSuperCategoryIdentifier().isEmpty()) {

            if (dto.getIdentifier().equals(dto.getSuperCategoryIdentifier())) {
                response.setSuccess(false);
                response.setMessage("Category cannot be its own parent");
                return response;
            }

            boolean exists = categoryRepository
                    .existsByIdentifier(dto.getSuperCategoryIdentifier());

            if (!exists) {
                response.setSuccess(false);
                response.setMessage("Invalid super category");
                return response;
            }
        }

        // set values
        category.setName(dto.getName());

        if (dto.getSuperCategoryIdentifier() == null
                || dto.getSuperCategoryIdentifier().isEmpty()) {

            category.setSuperCategoryIdentifier(null);
        } else {
            category.setSuperCategoryIdentifier(dto.getSuperCategoryIdentifier());
        }

        categoryRepository.save(category);

        response.setSuccess(true);
        return response;
    }

    // ================= DELETE =================

    @Override
    public void delete(String identifier) {

        Category category = categoryRepository
                .findByIdentifier(identifier)
                .orElse(null);

        if (category != null) {
            categoryRepository.delete(category);
        }
    }

    // ================= MAPPING =================

    private CategoryDto mapToDto(Category category) {

        CategoryDto dto = new CategoryDto();

        dto.setIdentifier(category.getIdentifier());
        dto.setName(category.getName());
        dto.setSuperCategoryIdentifier(category.getSuperCategoryIdentifier());

        return dto;
    }

    private void mapToEntity(CategoryDto dto, Category category) {

        category.setIdentifier(dto.getIdentifier());
        category.setName(dto.getName());

        if (dto.getSuperCategoryIdentifier() == null
                || dto.getSuperCategoryIdentifier().isEmpty()) {

            category.setSuperCategoryIdentifier(null);
        } else {
            category.setSuperCategoryIdentifier(dto.getSuperCategoryIdentifier());
        }
    }
}