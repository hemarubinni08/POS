package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto extends CommonDto {
    private boolean status;
    private long noOfItems;
    private long productId;
    private boolean isSuccess = true;
    private String message;
}
