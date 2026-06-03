package com.ust.pos.cart.service;

import com.ust.pos.dto.CartDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService {
    CartDto save(CartDto userDto);

    CartDto update(CartDto userDto);

    void delete(String username);

    List<CartDto> findAll(Pageable pageable);

    CartDto findByIdentifier(String identifier);

}
