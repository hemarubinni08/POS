package com.ust.pos;

import com.ust.pos.product.service.impl.ProductServiceImpl;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
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
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================

    @Test
    void saveTest_Success() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Product.class))
                .thenReturn(new Product());

        ProductDto response = productService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Mockito.verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    void saveTest_Failure_EmptyIdentifier() {

        ProductDto dto = new ProductDto();

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Identifier required", response.getMessage());
    }

    @Test
    void saveTest_Failure_AlreadyExists() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(new Product());

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Product already exists", response.getMessage());
    }

    // ================= FIND BY IDENTIFIER =================

    @Test
    void findByIdentifierTest() {

        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto mappedDto = new ProductDto();
        mappedDto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(mappedDto);

        ProductDto response = productService.findByIdentifier("P1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("P1", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        ProductDto response = productService.findByIdentifier("P1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Product not found", response.getMessage());
    }

    // ================= UPDATE =================

    @Test
    void updateTest_Success() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Product existing = new Product();
        existing.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(existing);

        Mockito.doNothing().when(modelMapper).map(dto, existing);

        Mockito.when(productRepository.save(existing))
                .thenReturn(existing);

        ProductDto response = productService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure_NotFound() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        ProductDto response = productService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Product not found", response.getMessage());
    }

    // ================= DELETE =================

    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(productRepository)
                .deleteByIdentifier("P1");

        productService.delete("P1");

        Mockito.verify(productRepository).deleteByIdentifier("P1");
    }

    // ================= FIND ALL =================

    @Test
    void findAllTest() {

        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(product));

        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(dto);

        List<ProductDto> result = productService.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("P1", result.get(0).getIdentifier());
    }

    // ================= TOGGLE STATUS =================

    @Test
    void toggleStatusTest_ActiveToInactive() {

        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus("ACTIVE");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        ProductDto response = productService.toggleStatus("P1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("INACTIVE", response.getStatus());
    }

    @Test
    void toggleStatusTest_InactiveToActive() {

        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus("INACTIVE");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        ProductDto response = productService.toggleStatus("P1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("ACTIVE", response.getStatus());
    }

    @Test
    void toggleStatus_NotFound() {

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        ProductDto response = productService.toggleStatus("P1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Product not found", response.getMessage());
    }

    // ================= ACTIVE PRODUCTS =================

    @Test
    void findActiveProductsTest() {

        Product active = new Product();
        active.setIdentifier("A");
        active.setStatus("ACTIVE");

        Product inactive = new Product();
        inactive.setIdentifier("B");
        inactive.setStatus("INACTIVE");

        ProductDto activeDto = new ProductDto();
        activeDto.setIdentifier("A");

        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(active, inactive));

        Mockito.when(modelMapper.map(active, ProductDto.class))
                .thenReturn(activeDto);

        List<ProductDto> result = productService.findActiveProducts();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("A", result.get(0).getIdentifier());
    }
}