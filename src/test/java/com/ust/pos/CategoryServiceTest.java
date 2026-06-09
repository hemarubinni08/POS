package com.ust.pos;

import com.ust.pos.category.service.impl.CategoryServiceImpl;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.WsDto;
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
        dto.setIdentifier("CAT1");
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);
        categoryService.save(dto);
        Mockito.verify(categoryRepository).save(Mockito.any(Category.class));
    }

    @Test
    void saveFailureTest() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(new Category());
        CategoryDto result = categoryService.save(dto);
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals("Category already exists", result.getMessage());
    }

    @Test
    void saveTest_SuperCategoryNull() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setSuperCategory(null);
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);
        categoryService.save(dto);
        Mockito.verify(categoryRepository).save(Mockito.argThat(cat -> cat.getSuperCategory() == null)
        );
    }

    @Test
    void saveTest_SuperCategoryBlank() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setSuperCategory("   ");
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);
        categoryService.save(dto);
        Mockito.verify(categoryRepository).save(Mockito.argThat(cat -> cat.getSuperCategory() == null)
        );
    }

    @Test
    void saveTest_WithSuperCategory() {
        CategoryDto dto = new CategoryDto();
        dto.setIdentifier("CAT1");
        dto.setSuperCategory("PARENT");
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(null);
        categoryService.save(dto);
        Mockito.verify(categoryRepository).save(Mockito.argThat(cat -> "PARENT".equals(cat.getSuperCategory()))
        );
    }

    @Test
    void updateTest() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier("OLD");
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier("NEW");
        Mockito.when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(existing));
        Mockito.when(categoryRepository.findByIdentifier("NEW")).thenReturn(null);
        categoryService.update(dto);
        Assertions.assertEquals("NEW", existing.getIdentifier());
        Mockito.verify(categoryRepository).save(existing);
    }

    @Test
    void updateTest_SuperCategoryNullOrBlank() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier("OLD");
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier("NEW");
        dto.setSuperCategory("   "); // ✅ blank
        Mockito.when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(existing));
        Mockito.when(categoryRepository.findByIdentifier("NEW")).thenReturn(null);
        categoryService.update(dto);
        Assertions.assertEquals("NEW", existing.getIdentifier());
        Assertions.assertNull(existing.getSuperCategory());
        Mockito.verify(categoryRepository).save(existing);
    }

    @Test
    void updateTest_IdentifierDifferent() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier("OLD");
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier("NEW"); // ✅ different
        Mockito.when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(existing));
        Mockito.when(categoryRepository.findByIdentifier("NEW")).thenReturn(null); // no duplicate
        categoryService.update(dto);
        Assertions.assertEquals("NEW", existing.getIdentifier());
        Mockito.verify(categoryRepository).save(existing);
    }

    @Test
    void updateTest_WithSuperCategoryValue() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier("OLD");
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier("NEW");
        dto.setSuperCategory("PARENT"); // ✅ value
        Mockito.when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(existing));
        Mockito.when(categoryRepository.findByIdentifier("NEW")).thenReturn(null);
        categoryService.update(dto);
        Assertions.assertEquals("NEW", existing.getIdentifier());
        Assertions.assertEquals("PARENT", existing.getSuperCategory());
        Mockito.verify(categoryRepository).save(existing);
    }

    @Test
    void updateFailureNotFoundTest() {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        CategoryDto result = categoryService.update(dto);
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals("Category not found", result.getMessage());
    }

    @Test
    void updateFailureDuplicateTest() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setIdentifier("OLD");
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setIdentifier("NEW");
        Mockito.when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(existing));
        Mockito.when(categoryRepository.findByIdentifier("NEW")).thenReturn(new Category());
        CategoryDto result = categoryService.update(dto);
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals("Category already exists", result.getMessage());
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteTest() {
        Mockito.when(categoryRepository.existsBySuperCategory("CAT1")).thenReturn(false);
        categoryService.delete("CAT1");
        Mockito.verify(categoryRepository).deleteByIdentifier("CAT1");
    }

    @Test
    void findByIdentifierTest() {
        Category category = new Category();
        CategoryDto dto = new CategoryDto();
        Mockito.when(categoryRepository.findByIdentifier("CAT1")).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(dto);
        CategoryDto result = categoryService.findByIdentifier("CAT1");
        Assertions.assertNotNull(result);
    }

    @Test
    void findAllTest() {

        // ✅ Arrange
        Category category = new Category();
        category.setIdentifier("Admin");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdentifier("Admin");

        List<Category> categories = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        // ✅ IMPORTANT: Mock TypeToken list mapping
        Mockito.when(modelMapper.map(
                        Mockito.eq(categories),
                        Mockito.any(java.lang.reflect.Type.class)))
                .thenReturn(categoryDtos);

        // ✅ Act
        WsDto<CategoryDto> response = categoryService.findAll(pageable);

        // ✅ Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getDtoList().size());
        Assertions.assertEquals("Admin", response.getDtoList().get(0).getIdentifier());

        Assertions.assertEquals(1, response.getTotalRecords());
        Assertions.assertEquals(1, response.getTotalPage());
        Assertions.assertEquals(50, response.getSizePerPage());
        Assertions.assertEquals(0, response.getPage());

        // ✅ Verify
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll(pageable);
        Mockito.verify(modelMapper, Mockito.times(1))
                .map(Mockito.eq(categories), Mockito.any(java.lang.reflect.Type.class));
    }

    @Test
    void findChildCategoriesTest() {
        List<Category> list = List.of(new Category());
        List<CategoryDto> dtoList = List.of(new CategoryDto());
        Mockito.when(categoryRepository.findBySuperCategoryIsNotNull()).thenReturn(list);
        Mockito.when(modelMapper.map(Mockito.eq(list), Mockito.any(java.lang.reflect.Type.class))).thenReturn(dtoList);
        List<CategoryDto> result = categoryService.findChildCategories();
        Assertions.assertEquals(1, result.size());
    }
}