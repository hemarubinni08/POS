package com.ust.pos;

import com.ust.pos.brand.service.impl.BrandServiceImpl;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.modell.Brand;
import com.ust.pos.modell.BrandRepository;
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
 class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("B1");

        Mockito.when(brandRepository.findByIdentifier("B1")).thenReturn(null);

        Brand brand = new Brand();
        brand.setIdentifier("B1");

        Mockito.when(modelMapper.map(brandDto, Brand.class)).thenReturn(brand);
        Mockito.when(brandRepository.save(brand)).thenReturn(brand);

        BrandDto response = brandService.save(brandDto);

        Assertions.assertEquals("B1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure_BrandAlreadyExists() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("B1");

        Brand existingBrand = new Brand();
        existingBrand.setIdentifier("B1");

        Mockito.when(brandRepository.findByIdentifier("B1")).thenReturn(existingBrand);

        BrandDto response = brandService.save(brandDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("Brand with identifier - B1 already exists", response.getMessage());
    }

    @Test
    void updateTest() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("B1");

        Brand existingBrand = new Brand();
        existingBrand.setIdentifier("B1");

        Mockito.when(brandRepository.findByIdentifier("B1")).thenReturn(existingBrand);
        Mockito.when(brandRepository.save(existingBrand)).thenReturn(existingBrand);

        BrandDto response = brandService.update(brandDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure_BrandNotFound() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("B1");

        Mockito.when(brandRepository.findByIdentifier("B1")).thenReturn(null);

        BrandDto response = brandService.update(brandDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("Brand with identifier - B1 not found", response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(brandRepository).deleteByIdentifier("B1");

        brandService.deleteByIdentifier("B1");

        Mockito.verify(brandRepository).deleteByIdentifier("B1");
    }

    @Test
    void findByIdentifierTest() {
        Brand brand = new Brand();
        brand.setIdentifier("B1");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("B1");

        Mockito.when(brandRepository.findByIdentifier("B1")).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandDto.class)).thenReturn(brandDto);

        BrandDto response = brandService.findByIdentifier("B1");

        Assertions.assertEquals("B1", response.getIdentifier());
    }

    @Test
    void findAllTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Admin");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        List<Brand> brands = List.of(brand);
        List<BrandDto> brandDtos = List.of(brandDto);

        Page<Brand> brandPage = new PageImpl<>(brands, PageRequest.of(0, 2), brands.size());

        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Mockito.when(brandRepository.findAll(pageable)).thenReturn(brandPage);
        Mockito.when(modelMapper.map(Mockito.eq(brands), Mockito.any(java.lang.reflect.Type.class))).thenReturn(brandDtos);

        List<BrandDto> response = brandService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }
}