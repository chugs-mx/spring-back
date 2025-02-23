package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.DiscountRepository
import com.chugs.chugs.repository.TicketRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TicketService {
    @Autowired
    TicketRepository ticketRepository

    @Autowired
    DiscountService discountService

    // Create ticket with discount
    Ticket createTicketWithDiscount(Ticket ticket, String discountName){
        Discount discount = discountService.getDiscountByName(discountName)
        ticket.setDiscount(discount)

        // Calculate total with discount apply
        double subtotal = ticket.getSubtotal()
        double discountAmount = discount.getAmount()
        double total = discount.getDiscountType() == "PERCENTAGE" ? subtotal - (subtotal * (discountAmount / 100 )) : subtotal - discountAmount
        ticket.setTotal(total)

        return ticketRepository.save(ticket)
    }

    // Obtain tickets by status
    public List<Ticket> getTicketByStatus(String status){
        return ticketRepository.findByTicketStatus(status)
    }
}
