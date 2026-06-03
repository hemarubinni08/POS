package com.ust.pos.modell;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart extends CommonFields{
    private BigDecimal originalPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private String coupon;
}
