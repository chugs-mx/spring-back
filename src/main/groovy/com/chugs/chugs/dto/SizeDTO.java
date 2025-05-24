package com.chugs.chugs.dto;

import java.math.BigDecimal;

public class SizeDTO {
    private Long id;
    private BigDecimal quantity;
    private String unitName;
    private String unitAbbreviation;

    public SizeDTO(Long id, BigDecimal quantity, String unitName, String unitAbbreviation) {
        this.id = id;
        this.quantity = quantity;
        this.unitName = unitName;
        this.unitAbbreviation = unitAbbreviation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitAbbreviation() {
        return unitAbbreviation;
    }

    public void setUnitAbbreviation(String unitAbbreviation) {
        this.unitAbbreviation = unitAbbreviation;
    }
}
