package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Stock extends CommonFields{

    private Integer availableQuantity;
    private Integer outgoingQuantity;
    private String warehouse;

}
