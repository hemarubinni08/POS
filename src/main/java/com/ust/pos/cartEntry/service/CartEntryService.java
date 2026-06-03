package com.ust.pos.cartEntry.service;

import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.dto.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CartEntryService {
    CartEntryDto save(CartEntryDto cartEntryDto);

    void delete(String cartId, String product);

    PaginationResponseDto<CartEntryDto> findAll(Pageable pageable);

    CartEntryDto findByIdentifier(String identifier);

    BigDecimal getSellingPriceAmount(String product);

    BigDecimal getDiscountPriceAmount(String product,BigDecimal quantity);

    BigDecimal getTotalPrice(String product, BigDecimal quantity);

    void recalculate(String cartId);

    List<CartEntryDto> findByCartId(String cartId);

    void deleteAllByCartId(String cartId);
}
