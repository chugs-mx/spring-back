package com.chugs.chugs.entity

import jakarta.persistence.*

import java.time.LocalDateTime

@Entity
@Table(name = "payment")
class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentMethod paymentMethod

    @Column(nullable = false)
    BigDecimal amountPaid

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    Ticket ticket

    @Column(nullable = false, name = "payment_date")
    LocalDateTime paymentDate

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD
    }
}
