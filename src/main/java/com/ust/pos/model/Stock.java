package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stock extends CommonFields {
    private int availableQuantity;
    private int outgoingQuantity;
    private String warehouse;


}
