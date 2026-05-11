package com.ust.pos;

import com.ust.pos.dto.BrandDto;
import com.ust.pos.model.Brand;
import com.ust.pos.model.BrandRepository;
import com.ust.pos.brand.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
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
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(null);

        Brand brand = new Brand();

        Mockito.when(modelMapper.map(brandDto, Brand.class)).thenReturn(brand);
        Mockito.when(brandRepository.save(brand)).thenReturn(brand);

        BrandDto response = brandService.save(brandDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());

        Mockito.verify(brandRepository).save(brand);
    }

    @Test
    void saveTestFailure() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Brand brand = new Brand();

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(brand);

        BrandDto response = brandService.save(brandDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());

        Mockito.verify(brandRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void findByIdentifierTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Admin");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(brand);
        Mockito.when(modelMapper.map(brand, BrandDto.class)).thenReturn(brandDto);

        BrandDto response = brandService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void findByIdentifierNullTest() {
        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        BrandDto response = brandService.findByIdentifier("Admin");

        Assertions.assertNull(response);
    }

    @Test
    void updateTest() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Brand existingBrand = new Brand();

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(existingBrand);
        Mockito.when(brandRepository.save(existingBrand)).thenReturn(existingBrand);

        BrandDto response = brandService.update(brandDto);

        Assertions.assertTrue(response.isSuccess());

        Mockito.verify(modelMapper).map(brandDto, existingBrand);
        Mockito.verify(brandRepository).save(existingBrand);
    }

    @Test
    void updateTestFailure() {
        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(null);

        BrandDto response = brandService.update(brandDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());

        Mockito.verify(brandRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(brandRepository).deleteByIdentifier("Admin");

        brandService.delete("Admin");

        Mockito.verify(brandRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Admin");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        List<Brand> brands = List.of(brand);
        List<BrandDto> brandDtos = List.of(brandDto);

        Mockito.when(brandRepository.findAll()).thenReturn(brands);
        Mockito.when(modelMapper.map(Mockito.eq(brands), Mockito.any(Type.class)))
                .thenReturn(brandDtos);

        List<BrandDto> response = brandService.findAll();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Admin", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusTest() {
        Brand brand = new Brand();
        brand.setIdentifier("Admin");
        brand.setStatus(false);

        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(brand);

        brandService.toggleStatus("Admin");

        Assertions.assertTrue(brand.isStatus());

        Mockito.verify(brandRepository).save(brand);
    }

    @Test
    void toggleStatusNotFoundTest() {
        Mockito.when(brandRepository.findByIdentifier("Admin")).thenReturn(null);

        brandService.toggleStatus("Admin");

        Mockito.verify(brandRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void findAll_WithPagination_ShouldReturnBrandDtos() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Brand> brands = List.of(new Brand());
        Page<Brand> brandPage = new PageImpl<>(brands);

        List<BrandDto> brandDtos = List.of(new BrandDto());

        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();

        Mockito.when(brandRepository.findAll(pageable))
                .thenReturn(brandPage);

        Mockito.when(modelMapper.map(brands, listType))
                .thenReturn(brandDtos);

        List<BrandDto> response = brandService.findAll(pageable);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());

        Mockito.verify(brandRepository).findAll(pageable);
        Mockito.verify(modelMapper).map(brands, listType);
    }
}