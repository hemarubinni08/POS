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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(null);

        when(brandRepository.save(any(Brand.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(new BrandDto());

        BrandDto response = brandService.save(dto);

        assertTrue(response.isSuccess());
        assertEquals("Brand created successfully", response.getMessage());
    }

    // ---------------- SAVE EMPTY NAME ----------------
    @Test
    void save_failure_empty_name() {

        BrandDto dto = new BrandDto();
        dto.setBrandName(" ");

        BrandDto response = brandService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Brand name is required", response.getMessage());

        verifyNoInteractions(brandRepository);
        verifyNoInteractions(modelMapper);
    }

    // ---------------- SAVE DUPLICATE ----------------
    @Test
    void save_failure_duplicate() {

        BrandDto dto = new BrandDto();
        dto.setBrandName("Nike");

        Brand existing = new Brand();
        existing.setDeleted(false);

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(existing);

        BrandDto response = brandService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Brand already exists", response.getMessage());
    }

    // ---------------- UPDATE SUCCESS ----------------
    @Test
    void update_success() {

        BrandDto dto = new BrandDto();
        dto.setIdentifier("Nike");
        dto.setBrandName("Nike Updated");

        Brand existing = new Brand();
        existing.setIdentifier("Nike");
        existing.setDeleted(false);

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(existing);

        when(brandRepository.save(any(Brand.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(new BrandDto());

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

    // ---------------- FIND BY IDENTIFIER ----------------
    @Test
    void find_success() {

        Brand brand = new Brand();
        brand.setDeleted(false);

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(brand);

        when(modelMapper.map(brand, BrandDto.class))
                .thenReturn(new BrandDto());

        BrandDto response = brandService.findByIdentifier("Nike");

        assertNotNull(response);
    }

    // ---------------- SOFT DELETE ----------------
    @Test
    void delete_success() {

        Brand brand = new Brand();
        brand.setIdentifier("Nike");

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(brand);

        when(brandRepository.save(any(Brand.class)))
                .thenAnswer(i -> i.getArgument(0));

        brandService.delete("Nike");

        assertTrue(brand.getDeleted());
        verify(brandRepository).save(brand);
    }

    // ---------------- FIND ALL PAGED ----------------
    @Test
    void find_all() {

        Brand brand = new Brand();
        Page<Brand> page = new PageImpl<>(List.of(brand));

        when(brandRepository.findByDeletedFalse(any(Pageable.class)))
                .thenReturn(page);

        when(modelMapper.map(anyList(), any()))
                .thenReturn(List.of(new BrandDto()));

        WsDto<BrandDto> result =
                brandService.findAll(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getDtoList().size());
    }

    // ---------------- ACTIVE BRANDS ----------------
    @Test
    void active_brands() {

        Brand brand = new Brand();
        brand.setStatus(true);
        brand.setDeleted(false);

        when(brandRepository.findByDeletedFalse())
                .thenReturn(List.of(brand));

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(new BrandDto());

        List<BrandDto> result = brandService.findActiveBrands();

        assertEquals(1, result.size());
    }

    // ---------------- TOGGLE STATUS ----------------
    @Test
    void toggle_success() {

        Brand brand = new Brand();
        brand.setIdentifier("Nike");
        brand.setStatus(false);
        brand.setDeleted(false);

        when(brandRepository.findByIdentifier("Nike"))
                .thenReturn(brand);

        when(brandRepository.save(any(Brand.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(modelMapper.map(any(Brand.class), eq(BrandDto.class)))
                .thenReturn(new BrandDto());

        BrandDto response = brandService.toggleStatus("Nike");

        assertTrue(response.isSuccess());
        assertEquals("Status updated successfully", response.getMessage());
    }
}