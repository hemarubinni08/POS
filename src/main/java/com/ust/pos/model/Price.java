package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.PrivateKey;

@Entity
@Getter
@Setter
public class Price extends CommonFields {
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal mrp;
}
