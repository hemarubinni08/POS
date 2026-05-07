package com.ust.pos.category.service.impl;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        categoryDto.setIdentifier(categoryDto.getIdentifier().trim());
        String identifier = categoryDto.getIdentifier().trim();
        if (categoryRepository.existsByIdentifier(identifier)) {
            categoryDto.setMessage("Already exists");
            categoryDto.setSuccess(false);
            return categoryDto;
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> findAll() {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(categoryRepository.findAll(), listType);
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        categoryRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public CategoryDto findByIdentifier(String identifier) {
        Category category = categoryRepository.findByIdentifier(identifier);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = categoryRepository.findByIdentifier(categoryDto.getIdentifier());
        modelMapper.map(categoryDto, category);
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public CategoryDto updateStatus(String identifier, boolean status) {
        Category category = categoryRepository.findByIdentifier(identifier);
        category.setStatus(status);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAllActive() {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(categoryRepository.findByStatus(true), listType);
    }

    @Override
    public List<CategoryDto> findAllActiveChilds() {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(categoryRepository.findByStatusTrueAndSuperCategoryIsNot(""), listType);
    }
}
