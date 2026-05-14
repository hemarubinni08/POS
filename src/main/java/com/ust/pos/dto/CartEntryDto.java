package com.ust.pos.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter

public class CartEntryDto extends CommonDto{

    private String cartId;
    private Long productId;
    private BigDecimal discount;
    private BigDecimal totalPrice;

}
