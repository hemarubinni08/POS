package com.ust.pos;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE SUCCESS =================
    @Test
    void save_success() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Product entity = new Product();
        Product saved = new Product();
        ProductDto responseDto = new ProductDto();

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        when(modelMapper.map(any(ProductDto.class), eq(Product.class)))
                .thenReturn(entity);

        when(productRepository.save(entity))
                .thenReturn(saved);

        when(modelMapper.map(saved, ProductDto.class))
                .thenReturn(responseDto);

        ProductDto result = productService.save(dto);

        assertTrue(result.isSuccess());
        assertEquals("Product saved successfully", result.getMessage());

        verify(productRepository).save(entity);
    }

    // ================= SAVE FAILURE =================
    @Test
    void save_failure_duplicate() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(new Product());

        ProductDto result = productService.save(dto);

        assertFalse(result.isSuccess());
        assertEquals("Product already exists", result.getMessage());

        verify(productRepository, never()).save(any());
    }

    // ================= UPDATE SUCCESS =================
    @Test
    void update_success() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Product existing = new Product();
        Product saved = new Product();
        ProductDto responseDto = new ProductDto();

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(existing);

        when(productRepository.save(existing))
                .thenReturn(saved);

        when(modelMapper.map(saved, ProductDto.class))
                .thenReturn(responseDto);

        ProductDto result = productService.update(dto);

        assertTrue(result.isSuccess());
        assertEquals("Product updated successfully", result.getMessage());

        verify(productRepository).save(existing);
    }

    // ================= FIND BY ID SUCCESS =================
    @Test
    void findByIdentifier_success() {

        Product product = new Product();
        ProductDto dto = new ProductDto();

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        when(modelMapper.map(product, ProductDto.class))
                .thenReturn(dto);

        ProductDto result = productService.findByIdentifier("P1");

        assertTrue(result.isSuccess());
    }

    // ================= FIND BY ID FAILURE =================
    @Test
    void findByIdentifier_failure() {

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        ProductDto result = productService.findByIdentifier("P1");

        assertFalse(result.isSuccess());
        assertEquals("Product not found", result.getMessage());
    }

    // ================= FIND ALL =================
    @Test
    void findAll_success() {

        List<Product> list = List.of(new Product());
        Page<Product> page = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 5);

        when(productRepository.findByDeletedFalse(pageable))
                .thenReturn(page);

        when(modelMapper.map(eq(list), any(Type.class)))
                .thenReturn(List.of(new ProductDto()));

        WsDto<ProductDto> result = productService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getDtoList().size());
        assertEquals(1, result.getTotalRecords());
    }

    // ================= DELETE (SOFT DELETE) =================
    @Test
    void delete_success() {

        Product product = new Product();

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        productService.delete("P1");

        assertTrue(product.getDeleted());
        verify(productRepository).save(product);
    }

    // ================= TOGGLE STATUS =================
    @Test
    void toggle_status_success() {

        Product product = new Product();
        product.setStatus(true);

        ProductDto dto = new ProductDto();

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        when(productRepository.save(product))
                .thenReturn(product);

        when(modelMapper.map(product, ProductDto.class))
                .thenReturn(dto);

        ProductDto result = productService.toggleStatus("P1");

        assertTrue(result.isSuccess());
        assertFalse(product.getStatus());
    }

    // ================= SEARCH PRODUCT =================
    @Test
    void search_product_success() {

        Product product = new Product();
        product.setIdentifier("P1");

        when(productRepository.searchActiveProducts("lap"))
                .thenReturn(List.of(product));

        when(priceRepository.countActivePriceTypes("P1"))
                .thenReturn(3L);

        when(modelMapper.map(any(Product.class), eq(ProductDto.class)))
                .thenReturn(new ProductDto());

        List<ProductDto> result = productService.searchProduct("lap");

        assertEquals(1, result.size());
    }

    // ================= SEARCH PRODUCT FILTERED =================
    @Test
    void search_product_filtered_out() {

        Product product = new Product();
        product.setIdentifier("P1");

        when(productRepository.searchActiveProducts("lap"))
                .thenReturn(List.of(product));

        when(priceRepository.countActivePriceTypes("P1"))
                .thenReturn(2L); // NOT 3 → should be filtered out

        List<ProductDto> result = productService.searchProduct("lap");

        assertTrue(result.isEmpty());
    }
}