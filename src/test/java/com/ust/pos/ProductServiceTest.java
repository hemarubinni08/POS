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
        product.setIdentifier("1001");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.findByIdentifier("1001");

        Assertions.assertEquals("1001", response.getIdentifier());
    }

    @Test
    void saveTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier(" 1001 ");
        dto.setProductName("IPHONE");

        Product product = new Product();

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(null);

        Mockito.when(modelMapper.map(dto, Product.class)).thenReturn(product);

        ProductDto response = productService.save(dto);

        Assertions.assertEquals("1001", response.getIdentifier());

        Mockito.verify(productRepository).save(product);
    }

    @Test
    void saveDuplicateIdentifierTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        Product existingProduct = new Product();

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(existingProduct);

        ProductDto response = productService.save(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Product with skuCode - 1001 already exists", response.getMessage());

        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
    }

    @Test
    void updateTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier(" 1001 ");
        dto.setProductName("IPHONE");

        Product existingProduct = new Product();
        existingProduct.setIdentifier("1001");

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(existingProduct);

        Mockito.doAnswer(invocation -> {

            ProductDto source = invocation.getArgument(0);
            Product target = invocation.getArgument(1);

            target.setIdentifier(source.getIdentifier());
            target.setProductName(source.getProductName());

            return null;

        }).when(modelMapper).map(Mockito.any(ProductDto.class), Mockito.any(Product.class));

        ProductDto response = productService.update(dto);

        Assertions.assertEquals(" 1001 ", response.getIdentifier());

        Mockito.verify(productRepository).save(existingProduct);
    }

    @Test
    void updateNotFoundTest() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(null);

        ProductDto response = productService.update(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals("Product with skuCode - 1001 not found", response.getMessage());

        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(Product.class));
    }

    @Test
    void deleteTest() {

        Mockito.doNothing().when(productRepository).deleteByIdentifier("1001");

        boolean result = productService.delete("1001");

        Assertions.assertTrue(result);

        Mockito.verify(productRepository).deleteByIdentifier("1001");
    }

    @Test
    void findAllTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Product product = new Product();
        product.setIdentifier("1001");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        List<Product> products = List.of(product);

        List<ProductDto> dtos = List.of(dto);

        Page<Product> productPage = new PageImpl<>(products);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(productPage);

        Mockito.when(modelMapper.map(Mockito.eq(products), Mockito.any(Type.class))).thenReturn(dtos);

        List<ProductDto> response = productService.findAll(pageable);

        Assertions.assertEquals(1, response.size());

        Assertions.assertEquals("1001", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusTrueToFalseTest() {

        Product product = new Product();
        product.setIdentifier("1001");
        product.setStatus(true);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.toggleStatus("1001");

        Assertions.assertEquals("1001", response.getIdentifier());

        Assertions.assertFalse(product.isStatus());

        Mockito.verify(productRepository).save(product);
    }

    @Test
    void toggleStatusFalseToTrueTest() {

        Product product = new Product();
        product.setIdentifier("1001");
        product.setStatus(false);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        Mockito.when(productRepository.findByIdentifier("1001")).thenReturn(product);

        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(dto);

        ProductDto response = productService.toggleStatus("1001");

        Assertions.assertEquals("1001", response.getIdentifier());

        Assertions.assertTrue(product.isStatus());

        Mockito.verify(productRepository).save(product);
    }

    @Test
    void findIfTrueTest() {

        Product product = new Product();
        product.setIdentifier("1001");
        product.setStatus(true);

        ProductDto dto = new ProductDto();
        dto.setIdentifier("1001");

        List<Product> products = List.of(product);

        List<ProductDto> dtos = List.of(dto);

        Mockito.when(productRepository.findByStatusIsTrue()).thenReturn(products);

        Mockito.when(modelMapper.map(Mockito.eq(products), Mockito.any(Type.class))).thenReturn(dtos);

        List<ProductDto> response = productService.findIfTrue();

        Assertions.assertEquals(1, response.size());

        Assertions.assertEquals("1001", response.get(0).getIdentifier());
    }
}