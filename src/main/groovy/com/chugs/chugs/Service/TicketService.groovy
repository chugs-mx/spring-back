package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.DiscountRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

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
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.")
        }
        println("OrderTable found: "+ order.getOrderId())

        BigDecimal subtotal = order.getTotal()
        BigDecimal total = subtotal
        Discount discount = null

        // Appply discount
        if( discountId != null ){
            discount = discountRepository.findById(discountId).orElse(null)
            if(!discount){
                throw new ResponseStatusException("Discount not found.")
            }

            println("Discount Type: " + discount.getDiscountType())
            BigDecimal discountAmount = calculateDiscount(subtotal, discount)
            total = subtotal.subtract(discountAmount)
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

        if(discount.getDiscountType() == Discount.DiscountType.PERCENTAGE){
            return subtotal.multiply(discount.getAmount().divide(BigDecimal.valueOf(100)))
        }else if(discount.getDiscountType() == Discount.DiscountType.FIXED){
            return discount.getAmount()
        }else{
            throw new IllegalArgumentException("Invalid discount type.")
        }

    }

    Ticket getTicketById(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
        if( !ticket ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Ticket not found.")
        }
    }
}
