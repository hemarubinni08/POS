package com.ust.pos;

import com.ust.pos.category.service.impl.CategoryServiceImpl;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.modell.Category;
import com.ust.pos.modell.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    public static final String CAT_1 = "CAT1";
    public static final String SUPER_CATEGORY = null;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier(CAT_1);

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(null);

        CategoryDto response = categoryService.save(categoryDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure_CategoryAlreadyExists() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier(CAT_1);

        Category existingCategory = new Category();
        existingCategory.setIdentifier(CAT_1);

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(existingCategory);

        CategoryDto response = categoryService.save(categoryDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Category already exists", response.getMessage());
    }

    @Test
    void saveWithNullSuperCategoryTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier(CAT_1);
        dto.setSuperCategory(null);

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(null);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveWithEmptySuperCategoryTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier(CAT_1);
        dto.setSuperCategory("   ");

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(null);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateWithNullSuperCategoryTest() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier(CAT_1);
        existing.setSuperCategory("OLD");

        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier(CAT_1);
        dto.setSuperCategory(null);

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(categoryRepository.save(existing))
                .thenReturn(existing);

        CategoryDto response = categoryService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateWithEmptySuperCategoryTest() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier(CAT_1);
        existing.setSuperCategory("OLD");

        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier(CAT_1);
        dto.setSuperCategory(" ");

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(categoryRepository.save(existing))
                .thenReturn(existing);

        CategoryDto response = categoryService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void findByIdentifier_WhenNullMappedTest() {
        Mockito.when(categoryRepository.findByIdentifier("CATX"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, CategoryDto.class))
                .thenReturn(null);

        CategoryDto response = categoryService.findByIdentifier("CATX");

        Assertions.assertNull(response);
    }

    @Test
    void saveCategory_WithNullSuperCategory() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier(CAT_1);
        dto.setSuperCategory(SUPER_CATEGORY);

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(null);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        Mockito.verify(categoryRepository).save(Mockito.any(Category.class));
    }

    @Test
    void saveCategory_WithEmptySuperCategory() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT2");
        dto.setSuperCategory("   "); // blank

        Mockito.when(categoryRepository.findByIdentifier("CAT2"))
                .thenReturn(null);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        Mockito.verify(categoryRepository).save(Mockito.any(Category.class));
    }

    @Test
    void saveCategory_WithSuperCategory() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT3");
        dto.setSuperCategory("PARENT");

        Mockito.when(categoryRepository.findByIdentifier("CAT3"))
                .thenReturn(null);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        Mockito.verify(categoryRepository).save(Mockito.any(Category.class));
    }

    @Test
    void saveCategory_WhenAlreadyExists() {

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier(CAT_1);

        Category existing = new Category();
        existing.setIdentifier(CAT_1);

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(existing);

        CategoryDto response = categoryService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Category already exists", response.getMessage());

        Mockito.verify(categoryRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void updateTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setIdentifier(CAT_1);
        categoryDto.setSuperCategory("PARENT");

        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setIdentifier(CAT_1);

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existingCategory));

        Mockito.when(categoryRepository.save(existingCategory))
                .thenReturn(existingCategory);

        CategoryDto response = categoryService.update(categoryDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure_CategoryNotFound() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        CategoryDto response = categoryService.update(categoryDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Category not found", response.getMessage());
    }

    @Test
    void updateTestFailure_DuplicateIdentifier() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setIdentifier("CAT2");

        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setIdentifier(CAT_1);

        Category duplicate = new Category();
        duplicate.setIdentifier("CAT2");

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existingCategory));

        Mockito.when(categoryRepository.findByIdentifier("CAT2"))
                .thenReturn(duplicate);

        CategoryDto response = categoryService.update(categoryDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Category already exists", response.getMessage());
    }

    @Test
    void updateWithoutIdentifierChangeTest() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier(CAT_1);

        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier(CAT_1);

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(categoryRepository.save(existing))
                .thenReturn(existing);

        CategoryDto response = categoryService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.when(categoryRepository.existsBySuperCategory(CAT_1))
                .thenReturn(false);

        categoryService.deleteByIdentifier(CAT_1);

        Mockito.verify(categoryRepository).deleteByIdentifier(CAT_1);
    }

    @Test
    void deleteTestFailure_WhenUsedAsSuperCategory() {
        Mockito.when(categoryRepository.existsBySuperCategory(CAT_1))
                .thenReturn(true);

        IllegalStateException ex = Assertions.assertThrows(
                IllegalStateException.class,
                () -> categoryService.deleteByIdentifier(CAT_1)
        );

        Assertions.assertEquals(
                "Cannot delete category. It is used as a super category.",
                ex.getMessage()
        );
    }

    @Test
    void findByIdentifierTest() {
        Category category = new Category();
        category.setIdentifier(CAT_1);

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier(CAT_1);

        Mockito.when(categoryRepository.findByIdentifier(CAT_1))
                .thenReturn(category);

        Mockito.when(modelMapper.map(category, CategoryDto.class))
                .thenReturn(dto);

        CategoryDto response = categoryService.findByIdentifier(CAT_1);

        Assertions.assertEquals(CAT_1, response.getIdentifier());
    }

    @Test
    void findByIdentifierNotFoundTest() {
        Mockito.when(categoryRepository.findByIdentifier("CATX"))
                .thenReturn(null);

        CategoryDto response = categoryService.findByIdentifier("CATX");

        Assertions.assertNull(response);
    }

    @Test
    void findAllTest() {
        Category category = new Category();
        category.setIdentifier(CAT_1);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier(CAT_1);

        List<Category> categories = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Category> categoryPage =
                new PageImpl<>(categories, pageable, categories.size());

        Mockito.when(categoryRepository.findAll(pageable))
                .thenReturn(categoryPage);

        Mockito.when(modelMapper.map(
                        Mockito.eq(categories),
                        Mockito.any(java.lang.reflect.Type.class)))
                .thenReturn(categoryDtos);

        List<CategoryDto> response = categoryService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findAllEmptyTest() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));

        Page<Category> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(categoryRepository.findAll(pageable))
                .thenReturn(emptyPage);

        Mockito.when(modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(Type.class)))
                .thenReturn(List.of());

        List<CategoryDto> response = categoryService.findAll(pageable);

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findChildCategoriesTest() {
        Category child = new Category();
        child.setIdentifier("CHILD");
        child.setSuperCategory("PARENT");

        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CHILD");

        Mockito.when(categoryRepository.findBySuperCategoryIsNotNull())
                .thenReturn(List.of(child));

        Mockito.when(modelMapper.map(
                        Mockito.any(List.class),
                        Mockito.any(Type.class)))
                .thenReturn(List.of(dto));

        List<CategoryDto> response = categoryService.findChildCategories();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("CHILD", response.get(0).getIdentifier());
    }

    @Test
    void findChildCategoriesEmptyTest() {
        Mockito.when(categoryRepository.findBySuperCategoryIsNotNull())
                .thenReturn(List.of());

        Mockito.when(modelMapper.map(
                        Mockito.any(List.class),
                        Mockito.any(Type.class)))
                .thenReturn(List.of());

        List<CategoryDto> response = categoryService.findChildCategories();

        Assertions.assertTrue(response.isEmpty());
    }
}