package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends CommonFields {

    private String name;
    private String sku;
    private String description;
    private String status;
}
