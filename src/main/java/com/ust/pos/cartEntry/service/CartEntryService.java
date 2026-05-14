package com.ust.pos.cartEntry.service;

import com.ust.pos.dto.CartEntryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartEntryService {
    CartEntryDto save(CartEntryDto cartEntryDto);

    CartEntryDto update(CartEntryDto cartEntryDto);

    List<CartEntryDto> findAll(Pageable pageable);

    CartEntryDto findByIdentifier(String identifier);

    List<CartEntryDto>findByCartId(String cartId);

    void delete(String identifier);
}
