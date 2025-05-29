package com.chugs.chugs.dto;

import com.chugs.chugs.entity.Size;

import java.math.BigDecimal;

public class IngredientDTO {
    private Long id;
    private String name;
    private BigDecimal quantity;
    private String size;

    public IngredientDTO(Long id, String name, BigDecimal quantity, String size) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
