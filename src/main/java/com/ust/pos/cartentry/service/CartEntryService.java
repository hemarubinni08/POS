package com.ust.pos.cartentry.service;

import com.ust.pos.dto.CartEntryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartEntryService {

    CartEntryDto save(CartEntryDto cartEntryDto);

    CartEntryDto update(CartEntryDto cartEntryDto);

    CartEntryDto findById(Long id);

    List<CartEntryDto> findAll(Pageable pageable);

    CartEntryDto delete(Long id);

}