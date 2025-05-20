package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "size")
class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    Unit unit

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal quantity

}
