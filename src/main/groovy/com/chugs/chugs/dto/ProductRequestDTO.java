package com.chugs.chugs.dto;

import com.chugs.chugs.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Product.Category category;
    private List<String> types;
    private String size;
    private List<String> defaultIngredients;

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

    public Product.Category getCategory() {
        return category;
    }

    public void setCategory(Product.Category category) {
        this.category = category;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getDefaultIngredients() {
        return defaultIngredients;
    }

    public void setDefaultIngredients(List<String> defaultIngredients) {
        this.defaultIngredients = defaultIngredients;
    }
}
