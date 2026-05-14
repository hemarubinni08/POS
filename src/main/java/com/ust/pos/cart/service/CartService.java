package com.ust.pos.cart.service;

import com.ust.pos.dto.CartDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService {

    CartDto save(CartDto cartDto);

    CartDto update(CartDto cartDto);

    CartDto findById(Long id);

    List<CartDto> findAll(Pageable pageable);

    CartDto delete(Long id);

}