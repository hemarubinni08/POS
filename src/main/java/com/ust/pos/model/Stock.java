package com.ust.pos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stock extends CommonFields {
    private String productIdentifier;
    private String warehouseIdentifier;
    private Integer availableQuantity;
    private Integer reorderLevel;
}