package com.ust.pos.product.service;

import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.ProductDto;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    ProductDto save(ProductDto userDto);

    ProductDto update(ProductDto userDto);

    void delete(String username);

    PaginationResponseDto<ProductDto> findAll(Pageable pageable);

    ProductDto findByIdentifier(String identifier);

}
