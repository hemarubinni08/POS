package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends CommonDto {
    private String productName;
    private String unit;
    private String status;
    private String brand;
    private String category;
}
