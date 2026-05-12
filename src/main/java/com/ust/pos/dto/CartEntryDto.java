package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartEntryDto extends CommonDto{
    private String product;
    private String quantity;
    private String coupon;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String cart;
    private BigDecimal discount;
}
