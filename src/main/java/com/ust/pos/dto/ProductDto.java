package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductDto extends CommonDto {

    private String sku;
    private String description;
    private List<String> categoryNames;
    private List<String> categoryIdentifiers;
}
