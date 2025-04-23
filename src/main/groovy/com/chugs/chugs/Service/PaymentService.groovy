package com.chugs.chugs.Service

import com.chugs.chugs.entity.Payment
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.PaymentRepository
import com.chugs.chugs.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class PaymentService {
    @Autowired
    PaymentRepository paymentRepository

    @Autowired
    TicketRepository ticketRepository

    @Transactional
    void processPayments(Long ticketId, List<Payment> payments) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException("Ticket not found."))
        System.out.println("Ticket found: " + ticket.getTicketId())

        BigDecimal totalPaid = BigDecimal.ZERO
        BigDecimal totalTips = BigDecimal.ZERO

        for (Payment payment : payments) {
            println "Processing payment: " + payment
            // Validate each payment
            if (payment.getPaymentMethod() == null) {
                throw new IllegalArgumentException("The payment method is required.")
            }
            if (payment.getAmountPaid() == null || payment.getAmountPaid() <= BigDecimal.ZERO) {
                throw new IllegalArgumentException("The amount paid must be greater than zero.")
            }
            if (payment.getTip() != null && payment.getTip().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("The tip cannot be negative.")
            }

            println "Processing payment: " + payment
            println "Total Paid: " + totalPaid
            println "Total Tips: " + totalTips
            // Calculate tip
            BigDecimal tipAmount = payment.getTip()
            totalTips = totalTips.add(tipAmount)
            totalPaid = totalPaid.add(payment.getAmountPaid())

            println "Despues de sumar"
            println "Total Paid: " + totalPaid
            println "Total Tips: " + totalTips

            // Save payments
            payment.setTicket(ticket)
            payment.setPaymentDate(LocalDateTime.now())
            paymentRepository.save(payment)
        }

        println "Ticket subtotal:" + ticket.getSubtotal()

        // Save ticket
        ticket.setTip(totalTips)
        ticket.setTotal(ticket.getSubtotal())
        ticketRepository.save(ticket)
        println "Ticket total: " + ticket.getTotal()


        // Validate if payment covers the ticket
        BigDecimal totalRegisteredPaid = paymentRepository.findByTicket(ticket).stream()
                .map(Payment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        println "Total registered paid: " +totalRegisteredPaid

        if (totalRegisteredPaid.compareTo(ticket.getTotal()) >= 0) {
            updateTicketStatusToPaid(ticketId)
        }
    }

    void updateTicketStatusToPaid(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found."))
        ticket.setTicketStatus(Ticket.TicketStatus.PAID)
        ticketRepository.save(ticket)

        println "Ticket " + ticketId + " is now PAID."
    }



 /**
    @Transactional
    void addOnlyPayment(Long ticketId, Payment payment, BigDecimal tipPercentage ) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow { new ResourceNotFoundException("Ticket not found.") }
        println "Ticket found: ${ticket.getTicketId()}"

        // Validate PaymentMethod
        if (payment.paymentMethod == null) {
            throw new IllegalArgumentException("The payment is required.")
        }

        // Validate AmountPaid
        if (payment.amountPaid == null || payment.amountPaid <= BigDecimal.ZERO) {
            throw new IllegalArgumentException("The amount paid must be greater than zero.")
        }

        // Calculate tip on subtotal
        BigDecimal tipAmount = ticket.getSubtotal() * (tipPercentage).divide(BigDecimal.valueOf(100))

        // Assign tip to ticket and update total
        ticket.setTip(tipAmount)
        ticket.setTotal(ticket.getSubtotal().add(tipAmount))
        ticketRepository.save(ticket)

        // Register payment
        payment.setTicket(ticket)
        payment.setPaymentDate(LocalDateTime.now() as LocalDateTime)

        println "Payment before save: " + String.valueOf(payment)
        paymentRepository.save(payment)

        // Validate if payment covers the ticket
        if (paymentRepository.findByTicket(ticket).stream()
                .map(Payment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add) >= ticket.getTotal()) {
            updateTicketStatusToPaid(ticketId);
        }
    }

    @Transactional
    void splitPayment(Long ticketId, List<Payment> payments){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow { new ResourceNotFoundException("Ticket not found.") }
        println "Ticket found: ${ticket.getTicketId()}"

        BigDecimal totalAmount = ticket.getTotal()
        BigDecimal totalPaid = BigDecimal.ZERO
        BigDecimal totalTips = BigDecimal.ZERO

        // Validate each payment
        for(Payment payment: payments){
            if(payment.getAmountPaid() <= BigDecimal.ZERO){
                throw new IllegalArgumentException(("The amount paid must be greater than zero."))
            }
            if(payment.getTip() < BigDecimal.ZERO){
                throw new IllegalArgumentException("The tip cannot be negative.")
            }

            totalPaid = totalPaid.add(payment.getAmountPaid())
            totalTips = totalTips.add(payment.getTip())

            payment.setTicket(ticket)
            payment.setPaymentDate(LocalDateTime.now())

            paymentRepository.save(payment)
        }

        // Validate if payments covers the ticket
        if (totalPaid != totalAmount) {
            throw new IllegalArgumentException("The sum of payments does not match the total ticket.")
        }
        updateTicketStatusToPaid(ticketId)
    } */
}
