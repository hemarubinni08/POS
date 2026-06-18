package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter

public class Order extends CommonFields {
    private String orderNumber;
    private String customerId;
    private BigDecimal originalPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private String coupon;
    private String orderStatus;
    private String paymentMode;
}