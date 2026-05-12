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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        product.setIdentifier("IPHONE");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.findByIdentifier("IPHONE");

        Assertions.assertEquals("IPHONE", response.getIdentifier());
    }

    @Test
    void saveTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier(" IPHONE ");
        dto.setSkuCode(1001L);

        Product product = new Product();

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(null);

        Mockito.when(productRepository.findBySkuCode(1001L)).thenReturn(null);

        Mockito.when(modelMapper.map(dto, Product.class)).thenReturn(product);

        ProductDto response = productService.save(dto);

        Assertions.assertEquals("IPHONE", response.getIdentifier());

        Mockito.verify(productRepository).save(product);
    }

    @Test
    void saveDuplicateIdentifierTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");
        dto.setSkuCode(1001L);

        Product existing = new Product();

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(existing);

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Product with identifier - IPHONE already exists", response.getMessage());
    }

    @Test
    void saveDuplicateSkuCodeTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");
        dto.setSkuCode(1001L);

        Product existingSku = new Product();

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(null);

        Mockito.when(productRepository.findBySkuCode(1001L)).thenReturn(existingSku);

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Product with skuCode - 1001 already exists", response.getMessage());
    }

    @Test
    void updateTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        Product existing = new Product();
        existing.setIdentifier("IPHONE");

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(existing);

        Mockito.doAnswer(invocation -> {

            ProductDto source = invocation.getArgument(0);
            Product target = invocation.getArgument(1);

            target.setIdentifier(source.getIdentifier());

            return null;

        }).when(modelMapper).map(Mockito.any(ProductDto.class), Mockito.any(Product.class));

        ProductDto response = productService.update(dto);

        Assertions.assertEquals("IPHONE", response.getIdentifier());

        Mockito.verify(productRepository).save(existing);
    }

    @Test
    void updateNotFoundTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(null);

        ProductDto response = productService.update(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Product with identifier - IPHONE not found", response.getMessage());
    }

    @Test
    void deleteTest() {

        Mockito.doNothing().when(productRepository).deleteByIdentifier("IPHONE");

        boolean result = productService.delete("IPHONE");

        Assertions.assertTrue(result);

        Mockito.verify(productRepository).deleteByIdentifier("IPHONE");
    }

    @Test
    void findAllTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Product product = new Product();
        product.setIdentifier("IPHONE");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        List<Product> products = List.of(product);
        List<ProductDto> dtos = List.of(dto);

        Page<Product> productPage = new PageImpl<>(products);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(productPage);

        Mockito.when(modelMapper.map(Mockito.eq(products), Mockito.any(Type.class))).thenReturn(dtos);

        List<ProductDto> response = productService.findAll(pageable);

        Assertions.assertEquals(1, response.size());

        Assertions.assertEquals("IPHONE", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusTrueToFalseTest() {

        Product product = new Product();
        product.setIdentifier("IPHONE");
        product.setStatus(true);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.toggleStatus("IPHONE");

        Assertions.assertEquals("IPHONE", response.getIdentifier());

        Assertions.assertFalse(product.isStatus());

        Mockito.verify(productRepository).save(product);
    }

    @Test
    void toggleStatusFalseToTrueTest() {

        Product product = new Product();
        product.setIdentifier("IPHONE");
        product.setStatus(false);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        Mockito.when(productRepository.findByIdentifier("IPHONE")).thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.toggleStatus("IPHONE");

        Assertions.assertEquals("IPHONE", response.getIdentifier());

        Assertions.assertTrue(product.isStatus());

        Mockito.verify(productRepository).save(product);
    }

    @Test
    void findIfTrueTest() {

        Product product = new Product();
        product.setIdentifier("IPHONE");
        product.setStatus(true);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("IPHONE");

        List<Product> products = List.of(product);
        List<ProductDto> dtos = List.of(dto);

        Mockito.when(productRepository.findByStatusIsTrue()).thenReturn(products);

        Mockito.when(modelMapper.map(Mockito.eq(products), Mockito.any(Type.class))).thenReturn(dtos);

        List<ProductDto> response = productService.findIfTrue();

        Assertions.assertEquals(1, response.size());

        Assertions.assertEquals("IPHONE", response.get(0).getIdentifier());
    }
}