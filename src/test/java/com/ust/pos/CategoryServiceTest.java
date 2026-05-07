package com.ust.pos;

import com.ust.pos.category.service.impl.CategoryServiceImpl;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
 class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    //  SAVE 

    @Test
    void saveTest_Success() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setName("Electronics");

        Mockito.when(categoryRepository.existsByIdentifier("CAT1"))
                .thenReturn(false);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTest_Failure_AlreadyExists() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.existsByIdentifier("CAT1"))
                .thenReturn(true);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Identifier already exists", response.getMessage());
    }

    //  FIND BY IDENTIFIER 

    @Test
    void findByIdentifierTest() {

        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1"))
                .thenReturn(Optional.of(category));

        Mockito.when(modelMapper.map(category, CategoryDto.class))
                .thenReturn(dto);

        CategoryDto response = categoryService.findByIdentifier("CAT1");

        Assertions.assertTrue(response.getIdentifier().equals("CAT1"));
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(categoryRepository.findByIdentifier("CAT1"))
                .thenReturn(Optional.empty());

        CategoryDto response = categoryService.findByIdentifier("CAT1");

        Assertions.assertFalse(response.isSuccess());
    }

    //  UPDATE 

    @Test
    void updateTest_Success() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setName("Updated");

        Category category = new Category();
        category.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1"))
                .thenReturn(Optional.of(category));

        Mockito.when(categoryRepository.save(category))
                .thenReturn(category);

        CategoryDto response = categoryService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1"))
                .thenReturn(Optional.empty());

        CategoryDto response = categoryService.update(dto);

        Assertions.assertFalse(response.isSuccess());
    }

    //  DELETE 

    @Test
    void deleteTest() {

        Category category = new Category();
        category.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findByIdentifier("CAT1"))
                .thenReturn(Optional.of(category));

        categoryService.delete("CAT1");

        Mockito.verify(categoryRepository).delete(category);
    }

    //  FIND ALL 

    @Test
    void findAllTest() {

        Category category = new Category();
        category.setIdentifier("CAT1");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");

        Mockito.when(categoryRepository.findAll())
                .thenReturn(List.of(category));

        Mockito.when(modelMapper.map(category, CategoryDto.class))
                .thenReturn(dto);

        List<CategoryDto> result = categoryService.findAll();

        Assertions.assertEquals(1, result.size());
    }

    //  SUPER CATEGORIES 

    @Test
    void findSuperCategoriesTest() {

        Category c1 = new Category();
        c1.setIdentifier("A");
        c1.setSuperCategoryIdentifier(null);

        Category c2 = new Category();
        c2.setIdentifier("B");
        c2.setSuperCategoryIdentifier("A");

        Mockito.when(categoryRepository.findAll())
                .thenReturn(List.of(c1, c2));

        Mockito.when(modelMapper.map(c1, CategoryDto.class))
                .thenReturn(new CategoryDto());

        List<CategoryDto> result = categoryService.findSuperCategories();

        Assertions.assertEquals(1, result.size());
    }

    //  LEAF 

    @Test
    void findLeafCategoriesTest() {

        Category parent = new Category();
        parent.setIdentifier("A");

        Category child = new Category();
        child.setIdentifier("B");
        child.setSuperCategoryIdentifier("A");

        Mockito.when(categoryRepository.findAll())
                .thenReturn(List.of(parent, child));

        Mockito.when(modelMapper.map(child, CategoryDto.class))
                .thenReturn(new CategoryDto());

        List<CategoryDto> result = categoryService.findLeafCategories();

        Assertions.assertEquals(1, result.size());
    }

    //  CHILD
    @Test
    void findChildCategoriesTest() {

        Category child = new Category();
        child.setIdentifier("B");
        child.setSuperCategoryIdentifier("A");

        Mockito.when(categoryRepository.findAll())
                .thenReturn(List.of(child));

        Mockito.when(modelMapper.map(child, CategoryDto.class))
                .thenReturn(new CategoryDto());

        List<CategoryDto> result = categoryService.findChildCategories();

        Assertions.assertEquals(1, result.size());
    }
}