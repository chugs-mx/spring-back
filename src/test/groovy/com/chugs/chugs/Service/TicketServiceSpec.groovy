package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.DiscountRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.TicketRepository
import spock.lang.Specification

class TicketServiceSpec extends Specification{
    DiscountRepository discountRepository = Mock()
    OrderTableRepository orderTableRepository = Mock()
    TicketRepository ticketRepository = Mock()

    TicketService ticketService = new TicketService(discountRepository: discountRepository, orderTableRepository: orderTableRepository, ticketRepository: ticketRepository)

    def"testCreateTicketWithoutDiscount"(){
        given:
        Long discountId = null
        OrderTable orderTable = new OrderTable(orderId: 1L, total: BigDecimal.valueOf(100))

        orderTableRepository.findById(orderTable.getOrderId()) >> Optional.of(orderTable)
        ticketRepository.save(_) >> { Ticket ticket ->
            ticket.ticketId = 1L
            return ticket
        }
        when:
        Ticket ticket = ticketService.createTicket(orderTable.getOrderId(), discountId)

        then:
        ticket.getSubtotal() == BigDecimal.valueOf(100)
        ticket.getTotal() == BigDecimal.valueOf(100)
        ticket.getDiscount() == null
        ticket.getTicketStatus() == Ticket.TicketStatus.PENDING
    }

    def"testCreateTicketApplyDiscount"(){
        given:
        Long discountId = 1L
        Long ticketId = 2L
        OrderTable orderTable = new OrderTable(orderId: 1L, total: BigDecimal.valueOf(100))
        Discount discount = new Discount(discountId: discountId, name: "CHUGS", amount: BigDecimal.valueOf(10), discountType: Discount.DiscountType.PERCENTAGE)

        orderTableRepository.findById(orderTable.getOrderId()) >> Optional.of(orderTable)
        ticketRepository.save(_) >> { Ticket ticket ->
            ticket.ticketId = 2L
            return ticket
        }
        discountRepository.findById(discountId) >> Optional.of(discount)

        when:
        Ticket ticket = ticketService.createTicket(orderTable.getOrderId(), discountId)

        then:
        ticket.getSubtotal() == BigDecimal.valueOf(100)
        ticket.getTotal() == BigDecimal.valueOf(90)
        ticket.discount == discount
        ticket.ticketStatus == Ticket.TicketStatus.PENDING

    }
}
