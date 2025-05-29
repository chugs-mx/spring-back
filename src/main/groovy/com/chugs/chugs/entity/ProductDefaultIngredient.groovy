package com.chugs.chugs.entity


import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table

@Entity
@Table(name = "product_default_ingredient")
class ProductDefaultIngredient {

    @EmbeddedId
    ProductDefaultIngredientId id

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "inventory_id")
    Inventory ingredient

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    Size size

    @Column(nullable = false)
    BigDecimal quantity


}

@Embeddable
class ProductDefaultIngredientId implements Serializable{
    Long productId
    Long ingredientId

    ProductDefaultIngredientId() {}

    ProductDefaultIngredientId(Long productId, Long ingredientId) {
        this.productId = productId
        this.ingredientId = ingredientId
    }

    @Override
    boolean equals(Object o) {
        if (this.is(o)) return true
        if (!(o instanceof ProductDefaultIngredientId)) return false
        ProductDefaultIngredientId that = (ProductDefaultIngredientId) o
        return productId == that.productId && ingredientId == that.ingredientId
    }

    @Override
    int hashCode() {
        return Objects.hash(productId, ingredientId)
    }
}

