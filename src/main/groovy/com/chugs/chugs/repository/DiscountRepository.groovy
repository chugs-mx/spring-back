package com.chugs.chugs.repository

import com.chugs.chugs.entity.Discount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface DiscountRepository extends JpaRepository<Discount,Long>{
    // Obtain existing discounts
    List<Discount> findByStartDateBeforeAndEndDateAfter(LocalDate todayStart,LocalDate todayEnd)

    // Find discount by name
    Optional<Discount> findByName(String name)

    // Check if there is a discount by id
    boolean existsById(Long discountId)
}
