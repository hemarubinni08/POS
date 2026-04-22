package com.ust.pos.product.service;

import com.ust.pos.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    ProductDto getProduct(Long id);

    List<ProductDto> getAllProducts();

    boolean deleteProduct(Long id);
}
