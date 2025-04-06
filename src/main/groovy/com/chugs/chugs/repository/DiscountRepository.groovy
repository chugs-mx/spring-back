package com.chugs.chugs.repository

import com.chugs.chugs.entity.Discount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.time.LocalDateTime

@Repository
interface DiscountRepository extends JpaRepository<Discount,Long>{
    // Obtain existing discounts
    List<Discount> findByStartDateBeforeAndEndDateAfter(LocalDateTime startDate, LocalDateTime endDay)

    // Find discount by name
    Optional<Discount> findByName(String name)
}
