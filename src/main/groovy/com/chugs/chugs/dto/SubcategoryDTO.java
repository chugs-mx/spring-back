package com.chugs.chugs.dto;

import java.util.List;

public class SubcategoryDTO {
    private Long id;
    private String name;
    private List<SizeDTO> subcategorySize;
    private List<IngredientDTO> defaultIngredients;

    public SubcategoryDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public SubcategoryDTO(Long id, String name, List<SizeDTO> subcategorySize, List<IngredientDTO> defaultIngredients) {
        this.id = id;
        this.name = name;
        this.subcategorySize = subcategorySize;
        this.defaultIngredients = defaultIngredients;
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

    public List<SizeDTO> getSubcategorySize() {
        return subcategorySize;
    }

    public void setSubcategorySize(List<SizeDTO> subcategorySize) {
        this.subcategorySize = subcategorySize;
    }

    public List<IngredientDTO> getDefaultIngredients() {
        return defaultIngredients;
    }

    public void setDefaultIngredients(List<IngredientDTO> defaultIngredients) {
        this.defaultIngredients = defaultIngredients;
    }
}
