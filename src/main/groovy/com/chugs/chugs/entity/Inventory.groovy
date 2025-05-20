package com.chugs.chugs.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inventory")
class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long inventoryId

    @Column(nullable = false, length = 100)
    String name

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_category_id", nullable = false)
    Category inventoryCategory

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_subcategory_id", nullable = false)
    Subcategory subcategory

    @Column(nullable = false, length = 500)
    String description

    @Column(nullable = false)
    LocalDateTime entryDate = LocalDateTime.now()

    @Column(nullable = true)
    LocalDateTime expiryDate

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_unit_id", nullable = false)
    Unit unitMeasure

    @Column(nullable = false)
    BigDecimal unitPrice

    @Column(nullable = false)
    BigDecimal quantity

}