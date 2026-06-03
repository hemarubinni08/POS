package com.ust.pos.model;

import com.ust.pos.dto.CartEntryDto;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart extends CommonFields{
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal coupon;
}
