package com.ust.pos.category.service.impl;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto dto) {

        if (dto.getIdentifier() == null || dto.getIdentifier().trim().isEmpty()) {
            dto.setSuccess(false);
            dto.setMessage("Category name is required");
            return dto;
        }

        if (categoryRepository.existsByIdentifier(dto.getIdentifier())) {
            dto.setSuccess(false);
            dto.setMessage("Category already exists");
            return dto;
        }

        Category category = modelMapper.map(dto, Category.class);
        categoryRepository.save(category);

        return dto;
    }


    @Override
    public CategoryDto updateCategory(CategoryDto dto) {

        Category category = categoryRepository.findById(dto.getId())
                .orElse(null);

        if (category == null) {
            dto.setSuccess(false);
            dto.setMessage("Category not found");
            return dto;
        }

        if (!category.getIdentifier().equals(dto.getIdentifier())
                && categoryRepository.existsByIdentifier(dto.getIdentifier())) {

            dto.setSuccess(false);
            dto.setMessage("Category identifier already exists");
            return dto;
        }

        category.setIdentifier(dto.getIdentifier());
        category.setCategory(dto.getCategory());

        categoryRepository.save(category);

        return dto;
    }


    @Override
    public CategoryDto getCategory(Long id) {

        CategoryDto dto = new CategoryDto();

        categoryRepository.findById(id).ifPresentOrElse(category -> {
            modelMapper.map(category, dto);
            dto.setSuccess(true);
        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Category not found");
        });

        return dto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(cat -> modelMapper.map(cat, CategoryDto.class))
                .toList();
    }

    @Override
    public boolean deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            return false;
        }

        categoryRepository.deleteById(id);
        return true;
    }
}