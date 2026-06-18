package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Order extends CommonFields {
    private String customer;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private BigDecimal receivedAmount;
    private BigDecimal changeAmount;
}
