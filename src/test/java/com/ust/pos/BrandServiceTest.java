package com.ust.pos;

import com.ust.pos.brand.service.impl.BrandServiceImpl;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.model.Brand;
import com.ust.pos.model.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
 class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================
    @Test
    void saveTest_Success() {

        BrandDto brandDto = new BrandDto();
        brandDto.setBrandName("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        Brand savedBrand = new Brand();
        savedBrand.setIdentifier("Admin");
        savedBrand.setBrandName("Admin");

        Mockito.when(brandRepository.save(Mockito.any(Brand.class)))
                .thenReturn(savedBrand);

        Mockito.when(modelMapper.map(savedBrand, BrandDto.class))
                .thenReturn(new BrandDto() {{
                    setIdentifier("Admin");
                    setBrandName("Admin");
                }});

        BrandDto response = brandService.save(brandDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void saveTest_Failure_AlreadyExists() {

        BrandDto brandDto = new BrandDto();
        brandDto.setBrandName("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(new Brand());

        BrandDto response = brandService.save(brandDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void saveTest_Failure_EmptyName() {

        BrandDto brandDto = new BrandDto();
        brandDto.setBrandName("");

        BrandDto response = brandService.save(brandDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Brand name is required", response.getMessage());
    }

    // ================= FIND BY IDENTIFIER =================
    @Test
    void findByIdentifierTest() {

        Brand brand = new Brand();
        brand.setIdentifier("Admin");

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(brand);

        Mockito.when(modelMapper.map(brand, BrandDto.class))
                .thenReturn(brandDto);

        BrandDto response = brandService.findByIdentifier("Admin");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        BrandDto response = brandService.findByIdentifier("Admin");

        Assertions.assertNull(response);
    }

    // ================= UPDATE =================
    @Test
    void updateTest_Success() {

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Brand existing = new Brand();
        existing.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(existing);

        // ✅ IMPORTANT FIX
        Mockito.doNothing().when(modelMapper).map(brandDto, existing);

        Mockito.when(brandRepository.save(existing))
                .thenReturn(existing);

        Mockito.when(modelMapper.map(existing, BrandDto.class))
                .thenReturn(new BrandDto() {{
                    setIdentifier("Admin");
                }});

        BrandDto response = brandService.update(brandDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure_NotFound() {

        BrandDto brandDto = new BrandDto();
        brandDto.setIdentifier("Admin");

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        BrandDto response = brandService.update(brandDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Brand not found", response.getMessage());
    }

    // ================= DELETE =================
    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(brandRepository)
                .deleteByIdentifier("Admin");

        brandService.delete("Admin");

        Mockito.verify(brandRepository)
                .deleteByIdentifier("Admin");
    }

    // ================= FIND ALL =================
    @Test
    void findAllTest() {

        Brand brand = new Brand();
        brand.setIdentifier("Admin");

        BrandDto dto = new BrandDto();
        dto.setIdentifier("Admin");

        Mockito.when(brandRepository.findAll())
                .thenReturn(List.of(brand));

        Mockito.when(modelMapper.map(brand, BrandDto.class))
                .thenReturn(dto);

        List<BrandDto> response = brandService.findAll();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Admin", response.get(0).getIdentifier());
    }

    // ================= ACTIVE BRANDS =================
    @Test
    void findActiveBrandsTest() {

        Brand active = new Brand();
        active.setIdentifier("A");
        active.setStatus(true);

        Brand inactive = new Brand();
        inactive.setIdentifier("B");
        inactive.setStatus(false);

        Mockito.when(brandRepository.findAll())
                .thenReturn(List.of(active, inactive));

        Mockito.when(modelMapper.map(active, BrandDto.class))
                .thenReturn(new BrandDto() {{
                    setIdentifier("A");
                    setStatus(true);
                }});

        List<BrandDto> result = brandService.findActiveBrands();

        Assertions.assertEquals(1, result.size());
    }

    // ================= TOGGLE =================
    @Test
    void toggleStatusTest() {

        Brand brand = new Brand();
        brand.setIdentifier("Admin");
        brand.setStatus(true);

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(brand);

        Mockito.when(brandRepository.save(brand))
                .thenReturn(brand);

        BrandDto response = brandService.toggleStatus("Admin");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getStatus());
    }

    @Test
    void toggleStatus_NotFound() {

        Mockito.when(brandRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        BrandDto response = brandService.toggleStatus("Admin");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Brand not found", response.getMessage());
    }
}