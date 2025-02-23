package com.chugs.chugs.repository

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.entity.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface TicketRepository extends JpaRepository<Ticket,Long>{
    // Obtain all tickets by status
    List<Ticket> findByTicketStatus(String status)

    // Find tickets by order date
    List<Ticket> findByOrderDate(LocalDate orderDate)

    // Obtain tickets with specific discount
    List<Ticket> findByDiscountDiscountId(Long discountId)

}