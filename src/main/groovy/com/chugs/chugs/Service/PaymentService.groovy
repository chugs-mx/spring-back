package com.chugs.chugs.Service

import com.chugs.chugs.entity.Payment
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.PaymentRepository
import com.chugs.chugs.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class PaymentService {
    @Autowired
    PaymentRepository paymentRepository

    @Autowired
    TicketRepository ticketRepository

    /**
     * Register a single payment
     *  - The tip is calculated on the ticket subtotal
     *  - The tip is stored in the tip field of the ticket and the total is updated
     */
    @Transactional
    Payment registerSinglePaymentWithTip(Long ticketId, Payment.PaymentMethod paymentMethod, BigDecimal amountPaid, BigDecimal tipPercentage ){
        Ticket ticket = ticketRepository.findById(ticketId) as Ticket

        if(! ticketRepository.existsById(ticketId)){
            throw new ResourceNotFoundException("Ticket not found")
        }

        // Calculate tip on subtotal
        BigDecimal tipAmount = ticket.getSubtotal().multiply(tipPercentage).divide(BigDecimal.valueOf(100))

        // Assign tip to ticket and update total
        ticket.setTip(tipAmount)
        ticket.setTotal(ticket.getSubtotal().add(tipAmount))
        ticketRepository.save(ticket)

        // Register payment
        Payment payment = new Payment()
        payment.setPaymentMethod(paymentMethod)
        payment.setAmountPaid(amountPaid)
        payment.setTip(BigDecimal.ZERO)
        payment.setTicket(ticket)
        payment.setPaymentDate(LocalDate.now() as LocalDateTime)

        return paymentRepository.save(payment)
    }

    /**
     *  Register various payments
     *   - Each payment can have its own tip
     */
    @Transactional
    List<Payment> divideBillWithIndividualTips(Long ticketId, List<Payment> payments){
        Ticket ticket = ticketRepository.findById(ticketId)
        if(! ticketRepository.existsById(ticketId)){
            throw new ResourceNotFoundException("Ticket not found")
        }

        ticket.setTip(BigDecimal.ZERO)
        ticketRepository.save(ticket)

        // Sum of amount and tip on each payment
        BigDecimal totalPaid = payments.sum { it.amountPaid + it.tipAmount } as BigDecimal

        if (totalPaid > ticket.total) {
            throw new IllegalArgumentException("El total de los pagos no puede ser mayor que el total del ticket.")
        }

        // Save each payment
        payments.each {
            it.ticket = ticket
            it.paymentDate = LocalDateTime.now()
            paymentRepository.save(it)
        }
        return payments
    }
}
