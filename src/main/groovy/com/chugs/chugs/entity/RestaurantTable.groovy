package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "restaurant_table")
class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long tableId

    @Column(nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    int tableNumber

    @Column(nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    int seatNumber

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TableState tableState

    enum TableState {
        AVAILABLE, OCCUPIED, RESERVED
    }
}
