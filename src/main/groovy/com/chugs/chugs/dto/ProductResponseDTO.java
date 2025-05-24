package com.chugs.chugs.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String sizeWithUnit;
    private CategoryDTO category;
    private SubcategoryDTO subcategory;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long productId, String name, String description, BigDecimal price, String sizeWithUnit, CategoryDTO category, SubcategoryDTO subcategory) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sizeWithUnit = sizeWithUnit;
        this.category = category;
        this.subcategory = subcategory;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getSizeWithUnit() {
        return sizeWithUnit;
    }

    public void setSizeWithUnit(String sizeWithUnit) {
        this.sizeWithUnit = sizeWithUnit;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public SubcategoryDTO getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubcategoryDTO subcategory) {
        this.subcategory = subcategory;
    }
}
