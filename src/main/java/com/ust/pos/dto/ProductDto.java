package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto extends CommonDto {
    private String sku;
    private List<String> categories;
    private String unit;
    private String brand;
    private String model;
}
