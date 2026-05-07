package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Stock extends CommonFields {
    private Integer quantity;
    private List<String> warehouses;
}
