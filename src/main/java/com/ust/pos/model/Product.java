package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends CommonFields {
    private String category;
    private Long supplierId;
    private String brand;
    private String unit;
    private String model;
}
