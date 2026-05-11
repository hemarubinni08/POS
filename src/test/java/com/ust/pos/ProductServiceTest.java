package com.ust.pos;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.modell.Product;
import com.ust.pos.modell.ProductRepository;
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
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        Product product = new Product();
        product.setIdentifier("P1");

        Mockito.when(modelMapper.map(productDto, Product.class))
                .thenReturn(product);
        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        ProductDto response = productService.save(productDto);

        Assertions.assertEquals("P1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        Product existingProduct = new Product();
        existingProduct.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(existingProduct);

        ProductDto response = productService.save(productDto);

        Assertions.assertEquals("P1", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifierTest() {
        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(productDto);

        ProductDto response = productService.findByIdentifier("P1");

        Assertions.assertEquals("P1", response.getIdentifier());
    }

    @Test
    void updateTest() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        Product existingProduct = new Product();
        existingProduct.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(existingProduct);
        Mockito.when(productRepository.save(existingProduct))
                .thenReturn(existingProduct);

        ProductDto response = productService.update(productDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        ProductDto response = productService.update(productDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(productRepository)
                .deleteByIdentifier("P1");

        productService.delete("P1");

        Mockito.verify(productRepository)
                .deleteByIdentifier("P1");
    }

    @Test
    void findAllTest() {
        Product product = new Product();
        product.setIdentifier("P1");

        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        List<Product> products = List.of(product);
        List<ProductDto> productDtos = List.of(productDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Product> productPage =
                new PageImpl<>(products, pageable, products.size());

        Mockito.when(productRepository.findAll(pageable))
                .thenReturn(productPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(products),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(productDtos);

        List<ProductDto> response = productService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {
        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus(true);

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        productService.toggleStatus("P1");

        Assertions.assertFalse(product.getStatus());
        Mockito.verify(productRepository).save(product);
    }

    @Test
    void findAllActiveTest() {
        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus(true);

        ProductDto productDto = new ProductDto();
        productDto.setIdentifier("P1");

        Mockito.when(productRepository.findByStatusTrue())
                .thenReturn(List.of(product));
        Mockito.when(modelMapper.map(product, ProductDto.class))
                .thenReturn(productDto);

        List<ProductDto> response = productService.findAllActive();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("P1", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusProductNotFoundTest() {

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> productService.toggleStatus("P1")
        );

        Assertions.assertEquals("product not found", ex.getMessage());
    }

    @Test
    void findByIdentifierNullTest() {

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, ProductDto.class))
                .thenReturn(null);

        ProductDto response = productService.findByIdentifier("P1");

        Assertions.assertNull(response);
    }

    @Test
    void toggleStatus_ShouldToggleStatus_WhenProductExists() {

        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus(true);

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        productService.toggleStatus("P1");

        Assertions.assertFalse(product.getStatus(), "Status should be toggled");

        Mockito.verify(productRepository, Mockito.times(1))
                .save(product);
    }

    @Test
    void toggleStatus_ShouldThrowException_WhenProductNotFound() {

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> productService.toggleStatus("P1")
        );

        Assertions.assertEquals("product not found", exception.getMessage());

        Mockito.verify(productRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void toggleStatus_shouldToggleAndSaveProduct() {

        Product product = new Product();
        product.setIdentifier("P1");
        product.setStatus(true);

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(product);

        productService.toggleStatus("P1");

        Assertions.assertFalse(product.getStatus(), "Status should be toggled");

        Mockito.verify(productRepository)
                .findByIdentifier("P1");

        Mockito.verify(productRepository)
                .save(product);

        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    void toggleStatus_shouldThrowException_whenProductNotFound() {

        Mockito.when(productRepository.findByIdentifier("P1"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> productService.toggleStatus("P1")
        );

        Assertions.assertEquals("product not found", ex.getMessage());

        Mockito.verify(productRepository)
                .findByIdentifier("P1");

        Mockito.verify(productRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void save_shouldMapAndSaveProduct() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P2");

        Product product = new Product();
        product.setIdentifier("P2");

        Mockito.when(productRepository.findByIdentifier("P2"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(dto, Product.class))
                .thenReturn(product);
        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        productService.save(dto);

        Mockito.verify(modelMapper).map(dto, Product.class);
        Mockito.verify(productRepository).save(product);
    }

    @Test
    void save_existingProduct_shouldNotSave() {

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P3");

        Mockito.when(productRepository.findByIdentifier("P3"))
                .thenReturn(new Product());

        productService.save(dto);

        Mockito.verify(productRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void update_shouldMapOntoExistingProduct() {

        Product existing = new Product();
        existing.setIdentifier("P4");

        ProductDto dto = new ProductDto();
        dto.setIdentifier("P4");

        Mockito.when(productRepository.findByIdentifier("P4"))
                .thenReturn(existing);
        Mockito.when(productRepository.save(existing))
                .thenReturn(existing);

        productService.update(dto);

        Mockito.verify(modelMapper).map(dto, existing);
        Mockito.verify(productRepository).save(existing);
    }

    @Test
    void findAllActive_emptyResult_shouldReturnEmptyList() {

        Mockito.when(productRepository.findByStatusTrue())
                .thenReturn(List.of());

        List<ProductDto> response = productService.findAllActive();

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());

        Mockito.verify(productRepository)
                .findByStatusTrue();

        Mockito.verify(modelMapper, Mockito.never())
                .map(Mockito.any(Product.class), Mockito.eq(ProductDto.class));
    }
}