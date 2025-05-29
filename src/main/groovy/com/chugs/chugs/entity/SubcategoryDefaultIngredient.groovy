package com.chugs.chugs.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "subcategory_default_ingredients")
class SubcategoryDefaultIngredient {
    @EmbeddedId
    SubcategoryDefaultIngredientId id

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @JsonBackReference
    Subcategory subcategory;

    @ManyToOne
    @MapsId("ingredient_id")
    Inventory ingredient;

    @ManyToOne
    @JoinColumn(name = "size_id")
    Size size;

    BigDecimal quantity;
}


@Embeddable
class SubcategoryDefaultIngredientId implements Serializable {
    Long subcategoryId;
    Long ingredientId
}
