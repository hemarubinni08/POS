package com.ust.pos.cart.service;

import com.ust.pos.dto.CartDto;
import com.ust.pos.dto.PaginationResponseDto;
import org.springframework.data.domain.Pageable;


public interface CartService {
    CartDto save(CartDto userDto);

    CartDto update(CartDto userDto);

    void delete(String username);

    PaginationResponseDto<CartDto> findAll(Pageable pageable);

    CartDto findByIdentifier(String identifier);
}