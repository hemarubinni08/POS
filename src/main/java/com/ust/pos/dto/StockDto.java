package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto extends CommonDto {

    private Integer availableQuantity;
    private Integer outgoingQuantity;
    private String warehouse;

}
