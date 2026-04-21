package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stock extends CommonFields {
    private boolean status;
    private long noOfItems;
    private long productId;
}
