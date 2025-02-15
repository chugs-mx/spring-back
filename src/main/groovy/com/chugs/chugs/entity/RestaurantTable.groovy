package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "restaurant_table")
class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long tableId

    @Column(name = "seat_number", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    int seatNumber

    @Enumerated(EnumType.STRING)
    @Column(name = "table_state", nullable = false)
    TableState tableState

    enum TableState {
        AVAILABLE, OCCUPIED, RESERVED
    }
}
