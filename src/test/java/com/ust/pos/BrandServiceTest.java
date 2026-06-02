package com.ust.pos;

import com.ust.pos.brand.service.impl.BrandServiceImpl;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.model.Brand;
import com.ust.pos.model.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    // ---------------- SAVE SUCCESS ----------------
    @Test
    void save_success() {

        BrandDto dto = new BrandDto();
        dto.setBrandName("Nike");
        dto.setDescription("Sports");
        dto.setStatus(true);

        Brand entity = new Brand();
        Brand saved = new Brand();

        when(brandRepository.findByIdentifier("Nike")).thenReturn(null);
        when(brandRepository.save(any(Brand.class))).thenReturn(saved);

        // IMPORTANT FIX: be flexible with mapping
        when(modelMapper.map(any(), eq(Brand.class))).thenReturn(entity);
        when(modelMapper.map(any(), eq(BrandDto.class))).thenReturn(dto);

        BrandDto response = brandService.save(dto);

        assertTrue(response.isSuccess());
        assertEquals("Brand added successfully", response.getMessage());
    }

    // ---------------- SAVE EMPTY ----------------
    @Test
    void save_failure_empty_name() {

        BrandDto dto = new BrandDto();
        dto.setBrandName(" ");

        BrandDto response = brandService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Brand name is required", response.getMessage());
    }

    // ---------------- SAVE DUPLICATE ----------------
    @Test
    void save_failure_duplicate() {

        BrandDto dto = new BrandDto();
        dto.setBrandName("Nike");

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(new Brand());

        BrandDto response = brandService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Brand already exists", response.getMessage());
    }

    // ---------------- UPDATE ----------------
    @Test
    void update_success() {

        BrandDto dto = new BrandDto();
        dto.setIdentifier("Nike");

        Brand existing = new Brand();
        existing.setIdentifier("Nike");

        Brand saved = new Brand();

        BrandDto responseDto = new BrandDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("Brand updated successfully");

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(existing);

        when(brandRepository.save(any(Brand.class)))
                .thenReturn(saved);

        // 🔥 FIX: avoid strict mismatch (IMPORTANT)
        when(modelMapper.map(any(), eq(BrandDto.class)))
                .thenReturn(responseDto);

        BrandDto response = brandService.update(dto);

        assertTrue(response.isSuccess());
        assertEquals("Brand updated successfully", response.getMessage());

        verify(brandRepository).save(existing);
    }

    // ---------------- UPDATE NOT FOUND ----------------
    @Test
    void update_failure_not_found() {

        BrandDto dto = new BrandDto();
        dto.setIdentifier("Nike");

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(null);

        BrandDto response = brandService.update(dto);

        assertFalse(response.isSuccess());
        assertEquals("Brand not found", response.getMessage());
    }

    // ---------------- FIND ----------------
    @Test
    void find_success() {

        Brand brand = new Brand();

        BrandDto dto = new BrandDto();

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(brand);

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(dto);

        BrandDto response = brandService.findByIdentifier("Nike");

        assertNotNull(response);
    }

    // ---------------- DELETE ----------------
    @Test
    void delete_test() {

        doNothing().when(brandRepository).deleteByIdentifier("Nike");

        brandService.delete("Nike");

        verify(brandRepository).deleteByIdentifier("Nike");
    }

    // ---------------- FIND ALL ----------------
    @Test
    void find_all() {

        Brand brand = new Brand();
        Page<Brand> page = new PageImpl<>(List.of(brand));

        when(brandRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        when(modelMapper.map(anyList(), any()))
                .thenReturn(List.of(new BrandDto()));

        WsDto<BrandDto> result =
                brandService.findAll(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getDtoList().size());
    }

    // ---------------- ACTIVE ----------------
    @Test
    void active_brands() {

        Brand brand = new Brand();
        brand.setStatus(true);

        when(brandRepository.findAll())
                .thenReturn(List.of(brand));

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(new BrandDto());

        List<BrandDto> result = brandService.findActiveBrands();

        assertEquals(1, result.size());
    }

    // ---------------- TOGGLE ----------------
    @Test
    void toggle_success() {

        Brand brand = new Brand();
        brand.setStatus(false);

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(brand);

        when(brandRepository.save(any(Brand.class)))
                .thenReturn(brand);

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(new BrandDto());

        BrandDto response = brandService.toggleStatus("Nike");

        assertTrue(response.isSuccess());
    }
}