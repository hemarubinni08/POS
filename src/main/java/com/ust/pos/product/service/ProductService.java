package com.ust.pos.product.service;

import com.ust.pos.dto.ProductDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductService {
    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    boolean delete(String identifier);

    List<ProductDto> findAll();

    ProductDto findByIdentifier(String identifier);

    List<ProductDto> findIfTrue();

    ProductDto toggleStatus(String identifier);
}
