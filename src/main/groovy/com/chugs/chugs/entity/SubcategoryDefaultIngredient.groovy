package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "subcategory_default_ingredients")
class SubcategoryDefaultIngredient {
    @EmbeddedId
    SubcategoryDefaultIngredientId id

    @ManyToOne
    @MapsId("subcategoryId")
    Subcategory subcategory;

    @ManyToOne
    @MapsId("ingredientId")
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
