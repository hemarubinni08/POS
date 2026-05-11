package com.ust.pos;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.impl.ProductServiceImpl;
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
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P001");
        Mockito.when(productRepository.findByIdentifier("P001"))
                .thenReturn(null);
        Product product = new Product();
        Mockito.when(modelMapper.map(dto, Product.class))
                .thenReturn(product);
        Mockito.when(productRepository.save(product))
                .thenReturn(product);
        ProductDto response = productService.save(dto);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Product created successfully", response.getMessage());
    }

    @Test
    void saveTestFailure() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P001");
        Mockito.when(productRepository.findByIdentifier("P001"))
                .thenReturn(new Product());
        ProductDto response = productService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P001");
        Product existing = new Product();
        existing.setIdentifier("P001");
        Mockito.when(productRepository.findByIdentifier("P001"))
                .thenReturn(existing);
        Mockito.when(productRepository.save(existing))
                .thenReturn(existing);
        ProductDto response = productService.update(dto);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Product updated successfully", response.getMessage());
    }

    @Test
    void updateTestFailure() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P001");
        Mockito.when(productRepository.findByIdentifier("P001"))
                .thenReturn(null);
        ProductDto response = productService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(productRepository)
                .deleteByIdentifier("P001");
        boolean result = productService.delete("P001");
        Mockito.verify(productRepository).deleteByIdentifier("P001");
        Assertions.assertTrue(result);
    }

    @Test
    void findAllTest() {
        Product product = new Product();
        product.setIdentifier("P001");
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P001");
        List<Product> products = List.of(product);
        List<ProductDto> productDtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Product> productPage =
                new PageImpl<>(products, pageable, products.size());
        Mockito.when(productRepository.findAll(pageable))
                .thenReturn(productPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(products),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(productDtos);
        List<ProductDto> response = productService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("P001", response.get(0).getIdentifier());
    }

    @Test
    void findByIdentifierTest() {
        Product product = new Product();
        product.setIdentifier("P001");
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P001");
        Mockito.when(productRepository.findByIdentifier("P001"))
                .thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(dto);
        ProductDto response = productService.findByIdentifier("P001");
        Assertions.assertEquals("P001", response.getIdentifier());
    }
}