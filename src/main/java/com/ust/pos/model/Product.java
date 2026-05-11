package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends CommonFields{
    private String description;
    private String category;
    private Long barcode;
    private String brand;
    private String models;


}
