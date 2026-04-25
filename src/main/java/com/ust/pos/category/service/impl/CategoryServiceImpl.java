package com.ust.pos.category.service.impl;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto findByIdentifier(String identifier) {
        return modelMapper.map(
                categoryRepository.findByIdentifier(identifier),
                CategoryDto.class
        );
    }

    @Override
    public List<CategoryDto> findBySuperCategoryNotNull() {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(categoryRepository.findBySuperCategoryIsNot(""), listType);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {

        String identifier = categoryDto.getIdentifier();
        Category existingCategory = categoryRepository.findByIdentifier(identifier);

        if (existingCategory != null) {
            categoryDto.setMessage("Category with identifier - " + identifier + " already exists");
            categoryDto.setSuccess(false);
            return categoryDto;
        }

        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);

        categoryDto.setMessage("Category created successfully");
        categoryDto.setSuccess(true);
        return categoryDto;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {

        String identifier = categoryDto.getIdentifier();
        Category existingCategory = categoryRepository.findByIdentifier(identifier);

        if (existingCategory == null) {
            categoryDto.setMessage("Category with identifier - " + identifier + " not found");
            categoryDto.setSuccess(false);
            return categoryDto;
        }

        modelMapper.map(categoryDto, existingCategory);
        categoryRepository.save(existingCategory);

        categoryDto.setMessage("Category updated successfully");
        categoryDto.setSuccess(true);
        return categoryDto;
    }

    @Override
    public boolean delete(String identifier) {
        categoryRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<CategoryDto> findAll() {

        Type listType = new TypeToken<List<CategoryDto>>() {}.getType();

        return modelMapper.map(
                categoryRepository.findAll(),
                listType
        );
    }


}