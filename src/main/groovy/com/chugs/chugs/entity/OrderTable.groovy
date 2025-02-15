package com.chugs.chugs.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "order_table")
class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId

    @Column(nullable = false)
    BigDecimal total

    @Column(nullable = false)
    LocalDateTime orderDate = LocalDateTime.now()

    @Column(nullable = false, columnDefinition = "SMALLINT")
    Integer partySize

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    RestaurantTable table
}
