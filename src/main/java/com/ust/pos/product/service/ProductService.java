package com.ust.pos.product.service;

import com.ust.pos.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductDto productDto);

    List<ProductDto> findAll();

    boolean delete(String identifier);

    ProductDto findByIdentifier(String identifier);

    ProductDto update(ProductDto productDto);

    List<ProductDto> findAllActive();

    ProductDto updateStatus(String identifier, boolean status);
}
