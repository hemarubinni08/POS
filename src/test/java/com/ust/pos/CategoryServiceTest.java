package com.ust.pos;

import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import com.ust.pos.category.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    /* ===================== SAVE ===================== */

    @Test
    void saveTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");


        Mockito.when(categoryRepository.findByIdentifier("Admin")).thenReturn(null);
        Category category=new Category();
        Mockito.when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto response = categoryService.save(categoryDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");


        Category existingCategory = new Category();
        existingCategory.setIdentifier("Admin");


        Mockito.when(categoryRepository.findByIdentifier("Admin"))
                .thenReturn(existingCategory);

        CategoryDto response = categoryService.save(categoryDto);

        Assertions.assertFalse(response.isSuccess());
    }

    /* ===================== FIND BY IDENTIFIER ===================== */

    @Test
    void findByIdentifierTest() {
        Category category = new Category();
        category.setIdentifier("Admin");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        Mockito.when(categoryRepository.findByIdentifier("Admin")).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto response = categoryService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    /* ===================== UPDATE ===================== */

    @Test
    void updateTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        Category existingCategory = new Category();
        existingCategory.setIdentifier("Admin");

        Mockito.when(categoryRepository.findByIdentifier("Admin"))
                .thenReturn(existingCategory);
        Mockito.when(categoryRepository.save(existingCategory))
                .thenReturn(existingCategory);

        CategoryDto response = categoryService.update(categoryDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        Mockito.when(categoryRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        CategoryDto response = categoryService.update(categoryDto);

        Assertions.assertFalse(response.isSuccess());
    }

    /* ===================== DELETE ===================== */

    @Test
    void deleteTest() {
        Mockito.doNothing().when(categoryRepository)
                .deleteByIdentifier("Admin");

        categoryService.delete("Admin");


    }

    /* ===================== FIND ALL ===================== */

    @Test
    void findAllTest() {
        Category category = new Category();
        category.setIdentifier("Admin");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        List<Category> categorys = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        Mockito.when(categoryRepository.findAll()).thenReturn(categorys);
        Mockito.when(modelMapper.map(
                Mockito.eq(categorys),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(categoryDtos);

        List<CategoryDto> response = categoryService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findByStatusTest(){
        Category category = new Category();
        category.setIdentifier("Admin");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        List<Category> categorys = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        Mockito.when(categoryRepository.findByStatusIsTrue()).thenReturn(categorys);
        Mockito.when(modelMapper.map(
                Mockito.eq(categorys),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(categoryDtos);

        List<CategoryDto> response = categoryService.findIfTrue();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleTestActive(){

        Category category = new Category();
        category.setStatus(false);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStatus(true);
        Mockito.when(categoryRepository.findByIdentifier("Admin")).thenReturn(category);
        Mockito.when(modelMapper.map(category,CategoryDto.class)).thenReturn(categoryDto);
        CategoryDto response = categoryService.toggleStatus("Admin");
        Assertions.assertTrue(response.isStatus());

    }

    @Test
    void toggleTestInactive(){

        Category category = new Category();
        category.setStatus(true);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStatus(false);
        Mockito.when(categoryRepository.findByIdentifier("Admin")).thenReturn(category);
        Mockito.when(modelMapper.map(category,CategoryDto.class)).thenReturn(categoryDto);
        CategoryDto response = categoryService.toggleStatus("Admin");
        Assertions.assertFalse(response.isStatus());

    }
    @Test
    void findBySuperCategoryNotNullTest(){
        Category category = new Category();
        category.setIdentifier("Admin");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        List<Category> categorys = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        Mockito.when(categoryRepository.findByStatusTrueAndSuperCategoryIsNot("")).thenReturn(categorys);
        Mockito.when(modelMapper.map(
                Mockito.eq(categorys),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(categoryDtos);

        List<CategoryDto> response = categoryService.findBySuperCategoryNotNull();

        Assertions.assertEquals(1, response.size());
    }
}
