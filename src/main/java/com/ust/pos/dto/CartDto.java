package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartDto extends CommonDto {
    private BigDecimal totalPrice;//sum of all prices after discount
    private BigDecimal totalDiscount;// sum of all discounts in the cartEntry
    private BigDecimal totalOriginalPrice;//sum of og price of all entries
    private String coupon;
}
