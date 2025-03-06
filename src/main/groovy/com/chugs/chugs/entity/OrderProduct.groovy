package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_product")
class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderProductId

    @Column(nullable = false)
    BigDecimal quantity

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus order_status

    @Column(nullable = false)
    Boolean notified

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    OrderTable order

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product


    enum OrderStatus{
        PREPARING, READY, DELIVERED
    }

}
