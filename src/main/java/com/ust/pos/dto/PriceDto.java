package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto extends CommonDto{

    private Double costPrice;
    private Double sellingPrice;
    private String productName;

}
