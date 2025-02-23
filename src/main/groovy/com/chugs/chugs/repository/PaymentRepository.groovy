package com.chugs.chugs.repository

import com.chugs.chugs.entity.Payment
import com.chugs.chugs.entity.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository extends JpaRepository<Payment,Long>{
    // Find payment by ticket
    List<Payment> findByTicket(Ticket ticket)

}