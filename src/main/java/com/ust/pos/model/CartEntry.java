package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class CartEntry extends CommonFields {
    private String product;
    private String quantity;
    private String coupon;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String cart;
    private BigDecimal discount;
}
