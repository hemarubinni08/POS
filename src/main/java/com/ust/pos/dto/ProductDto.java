package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto extends CommonDto {

    private String name;
    private String sku;
    private String description;
    private String status;
}
