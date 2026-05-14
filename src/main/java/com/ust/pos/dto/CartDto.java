package com.ust.pos.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class CartDto extends CommonDto{

    private BigDecimal totalPrice;
    private BigDecimal discount;
    private String coupon;

}