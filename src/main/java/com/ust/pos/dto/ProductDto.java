package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends CommonDto {
    private String sku;
    private String category;
    private String brand;
    private String model;
    private String unit;
}
