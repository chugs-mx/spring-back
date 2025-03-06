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
    InventoryType inventoryType

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

    enum InventoryType {
        INGREDIENT, EQUIPMENT, PACKAGING
    }

    enum UnitMeasure {
        KG, LTR, UNIT
    }
}
