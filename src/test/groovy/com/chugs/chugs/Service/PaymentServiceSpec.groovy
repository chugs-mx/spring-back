package com.chugs.chugs.Service

import com.chugs.chugs.entity.Payment
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.PaymentRepository
import com.chugs.chugs.repository.TicketRepository
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import spock.lang.Specification
import java.time.LocalDateTime

class PaymentServiceSpec extends Specification {
    TicketRepository ticketRepository = Mock()
    PaymentRepository paymentRepository = Mock()
    PaymentService paymentService = new PaymentService(ticketRepository: ticketRepository, paymentRepository: paymentRepository)

    def "processSinglePaymentSuccessfully"() {
        given: "Create ticket and payment"
        Long ticketId = 222L
        Ticket ticket = new Ticket(ticketId: ticketId, subtotal: BigDecimal.valueOf(100), total: BigDecimal.valueOf(100))

        // Payment already includes tip (total paid = subtotal tip)
        Payment payment = new Payment(
                paymentMethod: Payment.PaymentMethod.CASH,
                amountPaid: BigDecimal.valueOf(110), // Total with tip
                tip: BigDecimal.valueOf(10),
                paymentDate: LocalDateTime.now()
        )

        ticketRepository.findById(ticketId) >> Optional.of(ticket)
        paymentRepository.findByTicket(ticket) >> [payment]

        when: "The payment is added"
        paymentService.processPayments(ticket.ticketId, [payment])

        then: "One payment and ticket are saved"
        // The ticket is saved and then when it changes to PAID it is saved again
        2 * ticketRepository.save({ it.tip == BigDecimal.valueOf(10) && it.total == BigDecimal.valueOf(110) })
        1 * paymentRepository.save({ it.ticket == ticket && it.amountPaid == BigDecimal.valueOf(110) })

        and: "The ticket status is updated"
        ticket.getTicketStatus() == Ticket.TicketStatus.PAID
    }


}
