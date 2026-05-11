package com.ust.pos.category.service.impl;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    //  FIND ALL
    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        Page<Category> categoryPage =categoryRepository.findAll(pageable);
        return modelMapper.map(categoryPage.getContent(), listType);
    }

    //  FIND SUPER
    @Override
    public List<CategoryDto> findSuperCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> superList = new ArrayList<>();

        for (Category category : categoryList) {
            if (category.getSuperCategoryIdentifier() == null
                    || category.getSuperCategoryIdentifier().isEmpty()) {

                superList.add(modelMapper.map(category, CategoryDto.class));
            }
        }
        return superList;
    }

    //  FIND LEAF
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

            if (!isParent) {
                leafList.add(modelMapper.map(category, CategoryDto.class));
            }
        }
        return leafList;
    }

    //  FIND CHILD (NEW)
    @Override
    public List<CategoryDto> findChildCategories() {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> childList = new ArrayList<>();

        for (Category category : categoryList) {

            if (category.getSuperCategoryIdentifier() != null
                    && !category.getSuperCategoryIdentifier().isEmpty()) {

                childList.add(modelMapper.map(category, CategoryDto.class));
            }
        }
        return childList;
    }

    //  FIND BY ID
    @Override
    public CategoryDto findByIdentifier(String identifier) {

        CategoryDto dto = new CategoryDto();

        Category category = categoryRepository.findByIdentifier(identifier).orElse(null);

        if (category == null) {
            dto.setSuccess(false);
            dto.setMessage("Category not found");
            return dto;
        }

        return modelMapper.map(category, CategoryDto.class);
    }

    //  SAVE
    @Override
    public CategoryDto save(CategoryDto dto) {

        CategoryDto response = new CategoryDto();

        if (categoryRepository.existsByIdentifier(dto.getIdentifier())) {
            response.setSuccess(false);
            response.setMessage("Identifier already exists");
            return response;
        }

        Category category = new Category();
        category.setIdentifier(dto.getIdentifier());
        category.setName(dto.getName());
        category.setSuperCategoryIdentifier(dto.getSuperCategoryIdentifier());

        categoryRepository.save(category);

        response.setSuccess(true);
        return response;
    }

    //  UPDATE
    @Override
    public CategoryDto update(CategoryDto dto) {

        CategoryDto response = new CategoryDto();

        Category category = categoryRepository.findByIdentifier(dto.getIdentifier()).orElse(null);

        if (category == null) {
            response.setSuccess(false);
            response.setMessage("Category not found");
            return response;
        }

        category.setName(dto.getName());
        category.setSuperCategoryIdentifier(dto.getSuperCategoryIdentifier());

        categoryRepository.save(category);

        response.setSuccess(true);
        return response;
    }

    //  DELETE
    @Override
    public void delete(String identifier) {

        Category category = categoryRepository.findByIdentifier(identifier).orElse(null);

        if (category != null) {
            categoryRepository.delete(category);
        }
    }
}