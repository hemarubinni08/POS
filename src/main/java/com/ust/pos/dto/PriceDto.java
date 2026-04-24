package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto extends CommonDto {
    private String productId;
    private Double costPrice;
    private Double mrp;
    private Double sellingPrice;
}

