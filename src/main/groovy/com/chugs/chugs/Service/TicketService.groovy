package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.DiscountRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class TicketService {
    @Autowired
    TicketRepository ticketRepository

    @Autowired
    OrderTableRepository orderTableRepository

    @Autowired
    DiscountRepository discountRepository

    @Transactional
    Ticket createTicket(Long orderId, Long discountId){
        OrderTable order = orderTableRepository.findById(orderId).orElse(null)
        if(!order){
            throw new ResourceNotFoundException("Order not found.")
        }
        println("OrderTable found: "+ order.getOrderId())

        BigDecimal subtotal = order.getTotal()
        BigDecimal total = subtotal
        Optional discount = null

        // Appply discount
        if( discountId != null ){
            discount = discountRepository.findById(discountId)
            if(!discount){
                throw new ResourceNotFoundException("Discount not found.")
            }

            BigDecimal discountAmount = calculateDiscount(subtotal, discount)
            (total = subtotal.subtract(discountAmount))
        }

        // Save ticket
        Ticket ticket = new Ticket()
        ticket.setOrder(order)
        ticket.setSubtotal(subtotal)
        ticket.setTotal(total)
        ticket.setTip(BigDecimal.ZERO)
        ticket.setDiscount(discount)
        ticket.setOrderDate(LocalDateTime.now())
        ticket.setTicketStatus(Ticket.TicketStatus.PENDING)

        Ticket ticketSave = ticketRepository.save(ticket)
        println("Ticket save: " + ticketSave.ticketId)

        return ticketSave
    }

    BigDecimal calculateDiscount(BigDecimal subtotal, Discount discount){
        if( discount == null ){
            return BigDecimal.ZERO
        }

        if( discount.getDiscountType().equals('PERCENTAGE')){
            return subtotal.multiply(discount.getAmount().divide(BigDecimal.valueOf(100)))
        }else if( discount.getDiscountType().equals('FIXED')){
            return discount.getAmount()
        }else{
            throw new IllegalArgumentException("Invalid discount type.")
        }

    }

    Ticket getTicketById(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
        if( !ticket ) {
            throw new ResourceNotFoundException("Ticket not found.")
        }
    }
}
