package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends CommonFields {

    private String sku;
    private String description;
    private List<String> categoryNames;
    private List<String> categoryIdentifiers;

}
