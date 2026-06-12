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
    void saveTest() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("Admin");

        Mockito.when(productRepository.findByIdentifier("Admin")).thenReturn(null);
        Product product = new Product();
        Mockito.when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        ProductDto response = productService.save(productDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertEquals(true, response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("Admin");
        Product product = new Product();

        Mockito.when(productRepository.findByIdentifier("Admin")).thenReturn(product);
        ProductDto response = productService.save(productDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage(), "Message cannot be null");

        Assertions.assertEquals(false, response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        Product product = new Product();
        product.setIdentifier("Admin");

        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("Admin");

        Mockito.when(productRepository.findByIdentifier("Admin")).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto response = productService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void updateTest() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("Admin");

        Product existingProduct = new Product();
        existingProduct.setIdentifier("Admin");

        Mockito.when(productRepository.findByIdentifier("Admin"))
                .thenReturn(existingProduct);

        ProductDto response = productService.update(productDto);

        Assertions.assertEquals("Admin", response.getIdentifier());

        Mockito.verify(modelMapper)
                .map(productDto, existingProduct);

        Mockito.verify(productRepository)
                .save(existingProduct);
    }

    @Test
    void updateTestFailure() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("Admin");

        Mockito.when(productRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        ProductDto response = productService.update(productDto);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(productRepository)
                .deleteByIdentifier("Admin");

        productService.delete("Admin");

        Mockito.verify(productRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {
        Product product = new Product();
        product.setIdentifier("Admin");

        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("Admin");

        List<Product> products = List.of(product);
        List<ProductDto> productDtos = List.of(productDto);

        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(modelMapper.map(
                Mockito.eq(products),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(productDtos);

        List<ProductDto> response = productService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findAllWithPaginationShouldReturnProductDtos() {
        Pageable pageable = PageRequest.of(0, 10);

        Product product = new Product();
        product.setIdentifier("Admin");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("Admin");

        Page<Product> page =
                new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll(pageable))
                .thenReturn(page);

        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(dto);

        Page<ProductDto> response =
                productService.findAll(pageable, null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(
                1,
                response.getContent().size()
        );

        Assertions.assertEquals(
                "Admin",
                response.getContent().get(0).getIdentifier()
        );

        Mockito.verify(productRepository)
                .findAll(pageable);
    }

    @Test
    void findByIdentifierNullTest() {
        Mockito.when(productRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, ProductDto.class))
                .thenReturn(null);

        ProductDto response =
                productService.findByIdentifier("Admin");

        Assertions.assertNull(response);
    }

    @Test
    void findAllWithSearchShouldReturnProductDtos() {
        Pageable pageable = PageRequest.of(0, 10);

        Product product = new Product();
        product.setIdentifier("Admin");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("Admin");

        Page<Product> page =
                new PageImpl<>(List.of(product));

        Mockito.when(
                productRepository.findByIdentifierContainingIgnoreCase(
                        "Admin",
                        pageable
                )
        ).thenReturn(page);

        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(dto);

        Page<ProductDto> response =
                productService.findAll(pageable, "Admin");

        Assertions.assertEquals(
                1,
                response.getContent().size()
        );

        Assertions.assertEquals(
                "Admin",
                response.getContent().get(0).getIdentifier()
        );

        Mockito.verify(productRepository)
                .findByIdentifierContainingIgnoreCase(
                        "Admin",
                        pageable
                );
    }

    @Test
    void toggleStatusShouldFlipStatus() {
        Product product = new Product();
        product.setIdentifier("Admin");
        product.setStatus(true);

        Mockito.when(productRepository.findByIdentifier("Admin"))
                .thenReturn(product);

        productService.toggleStatus("Admin");

        Assertions.assertFalse(product.isStatus());

        Mockito.verify(productRepository)
                .save(product);
    }

    @Test
    void toggleStatusShouldDoNothingWhenProductNotFound() {
        Mockito.when(productRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        productService.toggleStatus("Admin");

        Mockito.verify(productRepository, Mockito.never())
                .save(Mockito.any());
    }
}