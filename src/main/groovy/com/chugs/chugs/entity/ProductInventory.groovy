package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "product_inventory")
class ProductInventory {
    @EmbeddedId
    ProductInventoryId id

    @Column(nullable = false)
    BigDecimal quantity

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_measure", nullable = false)
    UnitMeasure unitMeasure

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    Product productId

    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "inventory_id", nullable = false)
    Inventory inventory

}


@Embeddable
class ProductInventoryId implements Serializable {
    Long productId
    Long inventoryId

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false
        ProductInventoryId that = (ProductInventoryId) o
        return Objects.equals(productId, that.productId) &&
                Objects.equals(inventoryId, that.inventoryId)
    }

    @Override
    int hashCode() {
        return Objects.hash(productId, inventoryId)
    }
}
