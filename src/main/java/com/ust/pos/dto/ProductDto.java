package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto extends CommonDto {
    private List<String> categories;
    private long skucode;
    private String brand;
    private String model;

}
