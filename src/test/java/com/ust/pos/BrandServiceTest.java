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
    void findByIdentifierTest() {

        Brand brand = new Brand();
        brand.setIdentifier("BRAND_1");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("BRAND_1");

        Mockito.when(brandRepository.findByIdentifier("BRAND_1")).thenReturn(brand);

        Mockito.when(modelMapper.map(brand, BrandDto.class)).thenReturn(brandDto);

        BrandDto response = brandService.findByIdentifier("BRAND_1");

        Assertions.assertEquals("BRAND_1", response.getIdentifier());

        Mockito.verify(brandRepository).findByIdentifier("BRAND_1");
    }

    @Test
    void toggleStatusTest() {

        Brand brand = new Brand();
        brand.setIdentifier("BRAND_1");
        brand.setStatus(true);

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("BRAND_1");
        brandDto.setStatus(false);

        Mockito.when(brandRepository.findByIdentifier("BRAND_1")).thenReturn(brand);

        Mockito.when(brandRepository.save(brand)).thenReturn(brand);

        Mockito.when(modelMapper.map(brand, BrandDto.class)).thenReturn(brandDto);

        BrandDto response = brandService.toggleStatus("BRAND_1");

        Assertions.assertFalse(response.isStatus());

        Mockito.verify(brandRepository).save(brand);
    }

    @Test
    void saveTest() {

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier(" BRAND_1 ");

        Brand brand = new Brand();

        Mockito.when(brandRepository.findByIdentifier("BRAND_1")).thenReturn(null);

        Mockito.when(modelMapper.map(brandDto, Brand.class)).thenReturn(brand);

        BrandDto response = brandService.save(brandDto);

        Mockito.verify(brandRepository).save(brand);

        Assertions.assertEquals("BRAND_1", response.getIdentifier());
    }

    @Test
    void saveTestFailure() {

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("BRAND_1");

        Brand existingBrand = new Brand();

        Mockito.when(brandRepository.findByIdentifier("BRAND_1")).thenReturn(existingBrand);

        BrandDto response = brandService.save(brandDto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Brand with identifier - BRAND_1 already exists", response.getMessage());
    }

    @Test
    void updateTest() {

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("BRAND_1");

        Brand existingBrand = new Brand();
        existingBrand.setIdentifier("BRAND_1");

        Mockito.when(brandRepository.findByIdentifier("BRAND_1")).thenReturn(existingBrand);

        Mockito.when(brandRepository.save(existingBrand)).thenReturn(existingBrand);

        Mockito.doNothing().when(modelMapper).map(brandDto, existingBrand);

        BrandDto response = brandService.update(brandDto);

        Mockito.verify(brandRepository).save(existingBrand);

        Assertions.assertEquals("BRAND_1", response.getIdentifier());
    }

    @Test
    void updateTestFailure() {

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("BRAND_1");

        Mockito.when(brandRepository.findByIdentifier("BRAND_1")).thenReturn(null);

        BrandDto response = brandService.update(brandDto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Brand with identifier - BRAND_1 not found", response.getMessage());
    }

    @Test
    void deleteTest() {

        Mockito.doNothing().when(brandRepository).deleteByIdentifier("BRAND_1");

        boolean result = brandService.delete("BRAND_1");

        Assertions.assertTrue(result);

        Mockito.verify(brandRepository).deleteByIdentifier("BRAND_1");
    }

    @Test
    void findAllTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Brand brand = new Brand();
        BrandDto brandDto = new BrandDto();

        List<Brand> brands = List.of(brand);
        List<BrandDto> brandDtos = List.of(brandDto);

        Page<Brand> brandPage = new PageImpl<>(brands);

        Mockito.when(brandRepository.findAll(pageable)).thenReturn(brandPage);

        Mockito.when(modelMapper.map(Mockito.eq(brands), Mockito.any(java.lang.reflect.Type.class))).thenReturn(brandDtos);

        List<BrandDto> response = brandService.findAll(pageable);

        Assertions.assertEquals(1, response.size());

        Mockito.verify(brandRepository).findAll(pageable);
    }

    @Test
    void findIfTrueTest() {

        Brand brand = new Brand();
        brand.setStatus(true);

        BrandDto brandDto = new BrandDto();
        brandDto.setStatus(true);

        List<Brand> brands = List.of(brand);
        List<BrandDto> brandDtos = List.of(brandDto);

        Mockito.when(brandRepository.findByStatusIsTrue()).thenReturn(brands);

        Mockito.when(modelMapper.map(Mockito.eq(brands), Mockito.any(java.lang.reflect.Type.class))).thenReturn(brandDtos);

        List<BrandDto> response = brandService.findIfTrue();

        Assertions.assertEquals(1, response.size());

        Assertions.assertTrue(response.get(0).isStatus());

        Mockito.verify(brandRepository).findByStatusIsTrue();
    }
}