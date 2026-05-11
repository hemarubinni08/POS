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
        brandDto.setIdentifier("Nike");
        Mockito.when(brandRepository.findByIdentifier("Nike")).thenReturn(null);
        Brand brand = new Brand();
        Mockito.when(modelMapper.map(brandDto, Brand.class)).thenReturn(brand);
        Mockito.when(brandRepository.save(brand)).thenReturn(brand);
        BrandDto response = brandService.save(brandDto);
        Assertions.assertEquals("Nike", response.getIdentifier());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Nike");
        Brand existing = new Brand();
        Mockito.when(brandRepository.findByIdentifier("Nike")).thenReturn(existing);
        BrandDto response = brandService.save(brandDto);
        Assertions.assertEquals("Nike", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void updateTest() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Nike");
        Brand existing = new Brand();
        existing.setIdentifier("Nike");
        Mockito.when(brandRepository.findByIdentifier("Nike")).thenReturn(existing);
        Mockito.when(brandRepository.save(existing)).thenReturn(existing);
        BrandDto response = brandService.update(brandDto);
        Assertions.assertEquals("Nike", response.getIdentifier());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Nike");
        Mockito.when(brandRepository.findByIdentifier("Nike")).thenReturn(null);
        BrandDto response = brandService.update(brandDto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(brandRepository).deleteByIdentifier("Nike");
        boolean result = brandService.delete("Nike");
        Assertions.assertTrue(result);
        Mockito.verify(brandRepository).deleteByIdentifier("Nike");
    }

    @Test
    void findAllTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Nike");
        BrandDto dto = new BrandDto();
        dto.setIdentifier("Nike");
        List<Brand> brands = List.of(brand);
        List<BrandDto> dtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Brand> brandPage = new PageImpl<>(brands, pageable, brands.size());
        Mockito.when(brandRepository.findAll(pageable)).thenReturn(brandPage);
        Mockito.when(modelMapper.map(
                        Mockito.eq(brands),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(dtos);
        List<BrandDto> response = brandService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Nike", response.get(0).getIdentifier());
    }

    @Test
    void findByIdentifierTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Nike");
        BrandDto dto = new BrandDto();
        dto.setIdentifier("Nike");
        Mockito.when(brandRepository.findByIdentifier("Nike")).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandDto.class)).thenReturn(dto);
        BrandDto response = brandService.findByIdentifier("Nike");
        Assertions.assertEquals("Nike", response.getIdentifier());
    }

    @Test
    void toggleStatusTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Nike");
        brand.setStatus(true);
        Brand updated = new Brand();
        updated.setIdentifier("Nike");
        updated.setStatus(false);
        BrandDto dto = new BrandDto();
        dto.setIdentifier("Nike");
        dto.setStatus(false);
        Mockito.when(brandRepository.findByIdentifier("Nike")).thenReturn(brand);
        Mockito.when(brandRepository.save(brand)).thenReturn(updated);
        Mockito.when(modelMapper.map(updated, BrandDto.class)).thenReturn(dto);
        BrandDto response = brandService.toggleStatus("Nike");
        Assertions.assertFalse(response.isStatus());
    }
}