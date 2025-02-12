package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_extra_supply")
class OrderExtraSupply {
    @EmbeddedId
    OrderExtraSupplyId id

    @Column(nullable = false)
    BigDecimal quantity

    @ManyToOne
    @MapsId("orderProductId")
    @JoinColumn(name = "order_product_id", nullable = false)
    OrderProduct orderProduct

    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "supply_id", nullable = false)
    Inventory inventory
}

@Embeddable
class OrderExtraSupplyId implements Serializable {
    Long orderProductId
    Long inventoryId

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false
        OrderExtraSupplyId that = (OrderExtraSupplyId) o
        return Objects.equals(orderProductId, that.orderProductId) &&
                Objects.equals(inventoryId, that.inventoryId)
    }

    @Override
    int hashCode() {
        return Objects.hash(orderProductId, inventoryId)
    }
}
