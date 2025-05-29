package com.chugs.chugs.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Long subcategoryId;
    private Long sizeId;
    private List<IngredientDTO> defaultIngredients;

    public ProductRequestDTO(String name, String description, BigDecimal price, Long categoryId, Long subcategoryId, Long sizeId, List<IngredientDTO> defaultIngredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.sizeId = sizeId;
        this.defaultIngredients = defaultIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public List<IngredientDTO> getDefaultIngredients() {
        return defaultIngredients;
    }

    public void setDefaultIngredients(List<IngredientDTO> defaultIngredients) {
        this.defaultIngredients = defaultIngredients;
    }
}
