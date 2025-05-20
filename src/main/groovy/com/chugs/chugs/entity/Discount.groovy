package com.chugs.chugs.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "discount")
class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long discountId

    @Column(nullable = false, length = 50)
    String name

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DiscountType discountType

    @Column(nullable = false)
    BigDecimal amount

    @Column(name = "start_date", nullable = false)
    LocalDateTime startDate

    @Column(name = "end_date", nullable = false)
    LocalDateTime endDate

    enum DiscountType {
        FIXED, PERCENTAGE
    }
}
