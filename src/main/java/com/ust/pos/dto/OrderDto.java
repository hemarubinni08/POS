package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class OrderDto extends CommonDto {
    private String orderNumber;
    private String customerId;
    private BigDecimal originalPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private String coupon;
    private String orderStatus;
    private String paymentMode;
}