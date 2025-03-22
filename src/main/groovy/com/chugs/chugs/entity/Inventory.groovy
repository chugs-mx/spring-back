package com.chugs.chugs.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inventory")
class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long inventoryId

    @Column(nullable = false, length = 100)
    String name

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    InventoryCategory inventoryCategory

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    Subcategory subcategory

    @Column(nullable = false, length = 500)
    String description

    @Column(nullable = false)
    LocalDateTime entryDate = LocalDateTime.now()

    @Column(nullable = true)
    LocalDateTime expiryDate

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UnitMeasure unitMeasure

    @Column(nullable = false)
    BigDecimal unitPrice

    @Column(nullable = false)
    BigDecimal quantity

    enum InventoryCategory {
        CLUTTER, REFRIGERATED, CLEANING, DISPOSABLE, FROZEN
    }

    enum Subcategory{
        INGREDIENT, PRODUCT_VARIANT, PRODUCT, MEAT, VEGETABLES, DRINKS
    }

    enum UnitMeasure {
        KG, LTR, UNIT
    }
}