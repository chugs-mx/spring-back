package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "product_default_ingredients")
class ProductDefaultIngredient{
    @EmbeddedId
    ProductDefaultIngredientId id

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    Product product

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id", nullable = false)
    Inventory inventory
}


@Embeddable
class ProductDefaultIngredientId {
    Long productId
    Long ingredientId
}
