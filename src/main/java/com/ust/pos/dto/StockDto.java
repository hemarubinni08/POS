package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StockDto extends CommonDto {
    private Integer quantity;
    private LocalDateTime lastUpdated;
    private List<String> warehouses;
}
