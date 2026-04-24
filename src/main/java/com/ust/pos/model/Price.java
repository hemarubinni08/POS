package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Price extends CommonFields {
    private String productId;
    private Double costPrice;
    private Double mrp;
    private Double sellingPrice;
}