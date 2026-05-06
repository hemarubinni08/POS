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

import java.lang.reflect.Type;
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
    void findByIdentifierTest() {
        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(dto);

        CategoryDto response = categoryService.findByIdentifier("CAT1");

        Assertions.assertEquals("CAT1", response.getIdentifier());
    }

    @Test
    void findByIdentifierNullTest() {
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);

        CategoryDto response = categoryService.findByIdentifier("CAT1");

        Assertions.assertNull(response);
    }

    @Test
    void saveTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setSuperCategory("SUPER");

        Category category = new Category();
        category.setIdentifier("CAT1");
        category.setSuperCategory("SUPER");

        CategoryDto mappedDto = new CategoryDto();
        mappedDto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);
        Mockito.when(modelMapper.map(Mockito.any(Category.class), Mockito.eq(CategoryDto.class))).thenReturn(mappedDto);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("CAT1", response.getIdentifier());
    }

    @Test
    void saveDuplicateTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Category existing = new Category();

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(existing);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setSuperCategory("SUPER");

        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto mappedDto = new CategoryDto();
        mappedDto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(mappedDto);

        CategoryDto response = categoryService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateNotFoundTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);

        CategoryDto response = categoryService.update(dto);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(categoryRepository).deleteByIdentifier("CAT1");

        boolean response = categoryService.delete("CAT1");

        Assertions.assertTrue(response);
        Mockito.verify(categoryRepository).deleteByIdentifier("CAT1");
    }

    @Test
    void findAllTest() {
        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        List<Category> categories = List.of(category);
        List<CategoryDto> dtos = List.of(dto);

        Mockito.when(categoryRepository.findBySuperCategoryIsNotNull()).thenReturn(categories);
        Mockito.when(modelMapper.map(Mockito.eq(categories), Mockito.any(Type.class))).thenReturn(dtos);

        List<CategoryDto> response = categoryService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findSuperCategoriesTest() {
        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        List<Category> categories = List.of(category);
        List<CategoryDto> dtos = List.of(dto);

        Mockito.when(categoryRepository.findBySuperCategoryIsNull()).thenReturn(categories);
        Mockito.when(modelMapper.map(Mockito.eq(categories), Mockito.any(Type.class))).thenReturn(dtos);

        List<CategoryDto> response = categoryService.findSuperCategories();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {
        Category category = new Category();
        category.setIdentifier("CAT1");
        category.setSuperCategory("SUPER");
        category.setStatus(true);

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(dto);

        CategoryDto response = categoryService.toggleStatus("CAT1");

        Assertions.assertEquals("CAT1", response.getIdentifier());
        Mockito.verify(categoryRepository).save(category);
    }

    @Test
    void toggleStatusNoSuperCategoryTest() {
        Category category = new Category();
        category.setIdentifier("CAT1");
        category.setSuperCategory(null);

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(dto);

        CategoryDto response = categoryService.toggleStatus("CAT1");

        Assertions.assertEquals("CAT1", response.getIdentifier());
        Mockito.verify(categoryRepository, Mockito.never()).save(category);
    }

    @Test
    void toggleStatusNullTest() {
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);

        CategoryDto response = categoryService.toggleStatus("CAT1");

        Assertions.assertNull(response);
    }

    @Test
    void findIfTrueTest() {
        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        List<Category> categories = List.of(category);
        List<CategoryDto> dtos = List.of(dto);

        Mockito.when(categoryRepository.findByStatusIsTrue()).thenReturn(categories);
        Mockito.when(modelMapper.map(Mockito.eq(categories), Mockito.any(Type.class))).thenReturn(dtos);

        List<CategoryDto> response = categoryService.findIfTrue();

        Assertions.assertEquals(1, response.size());
    }
}