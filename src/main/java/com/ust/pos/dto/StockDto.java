package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto extends CommonDto {
    private int availableQuantity;
    private int outgoingQuantity;
    private String warehouse;

}
