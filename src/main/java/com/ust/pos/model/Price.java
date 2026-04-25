package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@Entity
public class Price  extends CommonFields{

    private BigDecimal mrp;
    private BigDecimal sellingPrice;
    private LocalDate effectiveFrom;

}
