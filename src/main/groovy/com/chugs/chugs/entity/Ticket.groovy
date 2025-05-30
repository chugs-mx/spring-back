package com.chugs.chugs.entity

import jakarta.persistence.*

import java.time.LocalDateTime

@Entity
@Table(name = "ticket")
class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ticketId

    @Column(nullable = false)
    BigDecimal subtotal

    @Column(nullable = false)
    BigDecimal total

    @Column(nullable = true)
    BigDecimal tip = BigDecimal.ZERO

    @Column(name = "order_date", nullable = false)
    LocalDateTime orderDate = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TicketStatus ticketStatus

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    OrderTable order

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = true)
    Discount discount

    enum TicketStatus {
        PENDING, PAID, CANCELED
    }
}
