package com.chugs.chugs.entity

import jakarta.persistence.*

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

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD
    }
}
