package com.ust.pos;

import com.ust.pos.brand.service.impl.BrandServiceImpl;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.model.Brand;
import com.ust.pos.model.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BrandServiceTest {
    @InjectMocks
    private BrandServiceImpl brandServiceImpl;
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;


    @Test
    void saveTest() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Brand brand = new Brand();
        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(null);
        Mockito.when(modelMapper.map(brandDto, Brand.class)).thenReturn(brand);
        BrandDto response = brandServiceImpl.save(brandDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNull(response.getMessage());

        assertTrue(response.isSuccess());

    }

    @Test
    void saveTestFailure() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Brand brand = new Brand();
        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(brand);
        BrandDto response = brandServiceImpl.save(brandDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage());

        assertFalse(response.isSuccess());

    }

    @Test
    void findByIdentifierTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Admin");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandDto.class)).thenReturn(brandDto);

        BrandDto response = brandServiceImpl.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void updateTest() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Brand existingBrand = new Brand();
        existingBrand.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(existingBrand);
        Mockito.when(brandRepository.save(existingBrand))
                .thenReturn(existingBrand);

        BrandDto response = brandServiceImpl.update(brandDto);

        assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        BrandDto response = brandServiceImpl.update(brandDto);

        assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {

        Mockito.doNothing().when(brandRepository)
                .deleteByIdentifier("Admin");

        brandServiceImpl.delete("Admin");

        Mockito.verify(brandRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {

        // Step 1: Create pageable
        Pageable pageable = PageRequest.of(0, 5);

        // Step 2: Mock Brand entity list
        Brand brand = new Brand();
        brand.setIdentifier("B1");

        List<Brand> brandList = List.of(brand);

        // Step 3: Mock Page
        Page<Brand> brandPage = new PageImpl<>(brandList);

        Mockito.when(brandRepository.findAll(pageable))
                .thenReturn(brandPage);

        // Step 4: Mock DTO list
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("B1");

        List<BrandDto> brandDtoList = List.of(brandDto);

        Mockito.when(modelMapper.map(
                        Mockito.eq(brandPage.getContent()),
                        Mockito.any(Type.class)))
                .thenReturn(brandDtoList);

        // Step 5: Call method
        List<BrandDto> result = brandServiceImpl.findAll(pageable);

        // Step 6: Assertions
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("B1", result.get(0).getIdentifier());

        // Step 7: Verify
        Mockito.verify(brandRepository).findAll(pageable);
    }

    @Test
    void toggleStatusTest_TrueToFalse() {

        Brand brand = new Brand();
        brand.setIdentifier("M1");
        brand.setStatus(true);

        Mockito.when(brandRepository.findByIdentifier("M1"))
                .thenReturn(brand);

        brandServiceImpl.toggleStatus("M1");

        assertFalse(brand.isStatus()); // toggled
        Mockito.verify(brandRepository).save(brand);
    }

    // ✅Case 2: false → true
    @Test
    void toggleStatusTest_FalseToTrue() {

        Brand brand = new Brand();
        brand.setIdentifier("M1");
        brand.setStatus(false);

        Mockito.when(brandRepository.findByIdentifier("M1"))
                .thenReturn(brand);

        brandServiceImpl.toggleStatus("M1");

        assertTrue(brand.isStatus()); // toggled
        Mockito.verify(brandRepository).save(brand);
    }

    //  Case 3: not found
    @Test
    void toggleStatusTest_NotFound() {

        Mockito.when(brandRepository.findByIdentifier("M1"))
                .thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> brandServiceImpl.toggleStatus("M1")
        );

        assertEquals("Brand not found", exception.getMessage());
    }

    @Test
    void findActiveBrandTest() {

        // Mock entity
        Brand model = new Brand();
        model.setIdentifier("M1");
        model.setStatus(true);

        // Mock DTO
        BrandDto dto = new BrandDto();
        dto.setIdentifier("M1");

        List<Brand> brandList = List.of(model);
        List<BrandDto> dtoList = List.of(dto);

        // Mock repository
        Mockito.when(brandRepository.findByStatusTrue())
                .thenReturn(brandList);

        // Mock mapper
        Mockito.when(modelMapper.map(
                Mockito.eq(brandList),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(dtoList);

        // Call method
        List<BrandDto> response = brandServiceImpl.findActiveBrands();

        // Assertions
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("M1", response.get(0).getIdentifier());

        // Verify interactions
        Mockito.verify(brandRepository).findByStatusTrue();
    }


}