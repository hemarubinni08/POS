package com.ust.pos;

import com.ust.pos.category.service.impl.CategoryServiceImpl;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void saveTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Electronics");
        Mockito.when(categoryRepository.findByIdentifier("Electronics"))
                .thenReturn(null);
        Category category = new Category();
        Mockito.when(modelMapper.map(dto, Category.class))
                .thenReturn(category);
        Mockito.when(categoryRepository.save(category))
                .thenReturn(category);
        CategoryDto response = categoryService.save(dto);
        Assertions.assertEquals("Electronics", response.getIdentifier());
    }

    @Test
    void saveTestFailure() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Electronics");
        Category existing = new Category();
        Mockito.when(categoryRepository.findByIdentifier("Electronics"))
                .thenReturn(existing);
        CategoryDto response = categoryService.save(dto);
        Assertions.assertEquals("Electronics", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void updateTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Electronics");
        Category existing = new Category();
        existing.setIdentifier("Electronics");
        Mockito.when(categoryRepository.findByIdentifier("Electronics"))
                .thenReturn(existing);
        Category mapped = new Category();
        Mockito.when(modelMapper.map(dto, Category.class))
                .thenReturn(mapped);
        Mockito.when(categoryRepository.save(mapped))
                .thenReturn(mapped);
        CategoryDto response = categoryService.update(dto);
        Assertions.assertEquals("Electronics", response.getIdentifier());
    }

    @Test
    void updateTestFailure() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Electronics");
        Mockito.when(categoryRepository.findByIdentifier("Electronics"))
                .thenReturn(null);
        CategoryDto response = categoryService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(categoryRepository)
                .deleteByIdentifier("Electronics");
        categoryService.delete("Electronics");
        Mockito.verify(categoryRepository)
                .deleteByIdentifier("Electronics");
    }

    @Test
    void findAllTest() {
        Category category = new Category();
        category.setIdentifier("Electronics");
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Electronics");
        List<Category> categories = List.of(category);
        List<CategoryDto> dtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Category> categoryPage =
                new PageImpl<>(categories, pageable, categories.size());
        Mockito.when(categoryRepository.findAll(pageable))
                .thenReturn(categoryPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(categories),
                        Mockito.any(java.lang.reflect.Type.class)
                )
        ).thenReturn(dtos);
        List<CategoryDto> response = categoryService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Electronics", response.get(0).getIdentifier());
    }

    @Test
    void findByIdentifierTest() {
        Category category = new Category();
        category.setIdentifier("Electronics");
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Electronics");
        Mockito.when(categoryRepository.findByIdentifier("Electronics"))
                .thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class))
                .thenReturn(dto);
        CategoryDto response = categoryService.findByIdentifier("Electronics");
        Assertions.assertEquals("Electronics", response.getIdentifier());
    }

    @Test
    void findBySuperCategoryNotNullTest() {
        Category category = new Category();
        category.setIdentifier("Mobiles");
        category.setSuperCategory("Electronics");
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("Mobiles");
        dto.setSuperCategory("Electronics");
        List<Category> categories = List.of(category);
        List<CategoryDto> dtos = List.of(dto);
        Mockito.when(categoryRepository.findBySuperCategoryIsNot(""))
                .thenReturn(categories);
        Mockito.when(modelMapper.map(
                Mockito.eq(categories),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(dtos);
        List<CategoryDto> response = categoryService.findBySuperCategoryNotNull();
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Mobiles", response.get(0).getIdentifier());
    }
}