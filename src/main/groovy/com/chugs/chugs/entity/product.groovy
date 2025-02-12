package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId

    @Column(nullable = false, length = 100)
    String name

    @Column(nullable = false, length = 500)
    String description

    @Column(nullable = false)
    BigDecimal price
}
