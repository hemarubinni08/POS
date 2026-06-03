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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    // ---------------- SAVE SUCCESS ----------------
    @Test
    void save_success() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Product entity = new Product();
        Product saved = new Product();

        ProductDto responseDto = new ProductDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("Product saved successfully");

        when(productRepository.findByIdentifier("P1")).thenReturn(null);
        when(modelMapper.map(any(ProductDto.class), eq(Product.class))).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(saved);
        when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(responseDto);

        ProductDto response = productService.save(dto);

        assertTrue(response.isSuccess());
        assertEquals("Product saved successfully", response.getMessage());
    }

    // ---------------- SAVE FAILURE (EXISTS) ----------------
    @Test
    void save_failure_exists() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        when(productRepository.findByIdentifier("P1"))
                .thenReturn(new Product());

        ProductDto response = productService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Product already exists", response.getMessage());
    }

    // ---------------- SAVE FAILURE EMPTY ----------------
    @Test
    void save_failure_empty_identifier() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier(" ");

        ProductDto response = productService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Identifier required", response.getMessage());
    }

    // ---------------- SAVE FAILURE NULL ----------------
    @Test
    void save_failure_null_identifier() {

        ProductDto dto = new ProductDto();

        ProductDto response = productService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Identifier required", response.getMessage());
    }

    // ---------------- FIND ----------------
    @Test
    void find_success() {

        Product product = new Product();

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");
        dto.setSuccess(true);

        when(productRepository.findByIdentifier("P1")).thenReturn(product);
        when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.findByIdentifier("P1");

        assertTrue(response.isSuccess());
        assertEquals("P1", response.getIdentifier());
    }

    @Test
    void find_not_found() {

        when(productRepository.findByIdentifier("P1")).thenReturn(null);

        ProductDto response = productService.findByIdentifier("P1");

        assertFalse(response.isSuccess());
        assertEquals("Product not found", response.getMessage());
    }

    // ---------------- UPDATE SUCCESS (FIXED) ----------------
    @Test
    void update_success() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Product existing = new Product();
        existing.setIdentifier("P1");

        Product updated = new Product();

        ProductDto responseDto = new ProductDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("Product updated successfully");

        when(productRepository.findByIdentifier("P1")).thenReturn(existing);

        // 🔥 FIX: map DTO → entity safely
        when(modelMapper.map(any(ProductDto.class), eq(Product.class)))
                .thenReturn(existing);

        when(productRepository.save(existing)).thenReturn(updated);

        // 🔥 FIX: entity → DTO
        when(modelMapper.map(any(Product.class), eq(ProductDto.class)))
                .thenReturn(responseDto);

        ProductDto response = productService.update(dto);

        assertTrue(response.isSuccess());
        assertEquals("Product updated successfully", response.getMessage());

        verify(productRepository).save(existing);
    }

    @Test
    void update_failure() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        when(productRepository.findByIdentifier("P1")).thenReturn(null);

        ProductDto response = productService.update(dto);

        assertFalse(response.isSuccess());
        assertEquals("Product not found", response.getMessage());
    }

    // ---------------- DELETE ----------------
    @Test
    void delete_test() {

        doNothing().when(priceRepository).deleteByProductId("P1");
        doNothing().when(productRepository).deleteByIdentifier("P1");

        productService.delete("P1");

        verify(priceRepository).deleteByProductId("P1");
        verify(productRepository).deleteByIdentifier("P1");
    }

    // ---------------- FIND ALL ----------------
    @Test
    void find_all_test() {

        List<Product> productList = List.of(new Product());
        Page<Product> page = new PageImpl<>(productList);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(modelMapper.map(anyList(), any(Type.class)))
                .thenReturn(List.of(new ProductDto()));

        WsDto<ProductDto> result =
                productService.findAll(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getDtoList().size());
    }

    // ---------------- ACTIVE PRODUCTS ----------------
    @Test
    void active_products_test() {

        List<Product> products = List.of(new Product());

        when(productRepository.findAll()).thenReturn(products);

        when(modelMapper.map(anyList(), any(Type.class)))
                .thenReturn(List.of(new ProductDto()));

        List<ProductDto> result = productService.findActiveProducts();

        assertNotNull(result);
    }

    // ---------------- TOGGLE ----------------
    @Test
    void toggle_success() {

        Product product = new Product();
        product.setStatus(true);

        ProductDto responseDto = new ProductDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("Status updated successfully");

        when(productRepository.findByIdentifier("P1")).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        when(modelMapper.map(any(Product.class), eq(ProductDto.class)))
                .thenReturn(responseDto);

        ProductDto response = productService.toggleStatus("P1");

        assertTrue(response.isSuccess());
        assertEquals("Status updated successfully", response.getMessage());
    }

    @Test
    void toggle_failure() {

        when(productRepository.findByIdentifier("P1")).thenReturn(null);

        ProductDto response = productService.toggleStatus("P1");

        assertFalse(response.isSuccess());
        assertEquals("Product not found", response.getMessage());
    }
}