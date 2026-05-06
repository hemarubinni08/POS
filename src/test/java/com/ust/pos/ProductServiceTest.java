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

import java.lang.reflect.Type;
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
    void findByIdentifierTest() {
        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.findByIdentifier("P1");

        Assertions.assertEquals("P1", response.getIdentifier());
    }

    @Test
    void saveTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier(" P1 ");
        dto.setSkuCode(100L);

        Product product = new Product();

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(null);
        Mockito.when(productRepository.findBySkuCode(100L)).thenReturn(null);
        Mockito.when(modelMapper.map(Mockito.any(ProductDto.class), Mockito.eq(Product.class))).thenReturn(product);

        ProductDto response = productService.save(dto);

        Assertions.assertEquals("P1", response.getIdentifier().trim());
        Mockito.verify(productRepository).save(product);
    }

    @Test
    void saveDuplicateIdentifierTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");
        dto.setSkuCode(100L);

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(new Product());

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void saveDuplicateSkuTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");
        dto.setSkuCode(100L);

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(null);
        Mockito.when(productRepository.findBySkuCode(100L)).thenReturn(new Product());

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Product product = new Product();

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(product);

        ProductDto response = productService.update(dto);

        Assertions.assertEquals("P1", response.getIdentifier());
        Mockito.verify(productRepository).save(product);
    }

    @Test
    void updateNotFoundTest() {
        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(null);

        ProductDto response = productService.update(dto);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(productRepository).deleteByIdentifier("P1");

        boolean response = productService.delete("P1");

        Assertions.assertTrue(response);
        Mockito.verify(productRepository).deleteByIdentifier("P1");
    }

    @Test
    void findAllTest() {
        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        List<Product> products = List.of(product);
        List<ProductDto> dtos = List.of(dto);

        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(modelMapper.map(Mockito.eq(products), Mockito.any(Type.class))).thenReturn(dtos);

        List<ProductDto> response = productService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {
        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus(true);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1")).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.toggleStatus("P1");

        Assertions.assertEquals("P1", response.getIdentifier());
        Mockito.verify(productRepository).save(product);
    }

    @Test
    void findIfTrueTest() {
        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P1");

        List<Product> products = List.of(product);
        List<ProductDto> dtos = List.of(dto);

        Mockito.when(productRepository.findByStatusIsTrue()).thenReturn(products);
        Mockito.when(modelMapper.map(Mockito.eq(products), Mockito.any(Type.class))).thenReturn(dtos);

        List<ProductDto> response = productService.findIfTrue();

        Assertions.assertEquals(1, response.size());
    }
}