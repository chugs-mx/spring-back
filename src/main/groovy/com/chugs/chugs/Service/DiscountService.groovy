package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.repository.DiscountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class DiscountService {
    @Autowired
    DiscountRepository discountRepository

    // Obtain discount current
    List<Discount> getCurrentDiscounts(LocalDateTime today){
        List<Discount> allDiscounts = discountRepository.findByStartDateBeforeAndEndDateAfter(today, today)

        //Filter discounts with inconsistent dates
        allDiscounts = allDiscounts.findAll { discount ->
            // If the discount has inconsistent dates, we do not include it
            !discount.startDate.isAfter(discount.endDate)
        }

        // Add only discounts that are active for the today date
        allDiscounts = allDiscounts.findAll { discount ->
            discount.startDate.isBefore(today) && discount.endDate.isAfter(today)
        }

        return allDiscounts
    }

    // Obtain discount by name
    Discount getDiscountByName(String name){
        def discount  = discountRepository.findByName(name)
        if(!discount.isPresent()){
            throw new ResourceNotFoundException("Discount not found")
        }
        return discount.get()
    }

    // Create or update discount
    Discount saveDiscount(Discount discount){
        if(discount.getName() == null || discount.getName().trim().isEmpty() ){
            throw new IllegalArgumentException("Discount name cannot be null or empty")
        }
        if( discount.getDiscountType() == null ){
            throw new IllegalArgumentException("Discount name cannot be null or empty")
        }
        if( discount.getAmount() == null || discount.getAmount() <= BigDecimal.ZERO ){
            throw new IllegalArgumentException("Discount amount must be greater than zero")
        }
        if( discount.getStartDate() == null ){
            throw new IllegalArgumentException("Start date cannot be null")
        }
        if( discount.getEndDate() == null ){
            throw new IllegalArgumentException("End date cannot be null")
        }

        if (discount.getStartDate().isAfter(discount.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date")
        }

        return discountRepository.save(discount)
    }

    // Delete discount
    void deleteDiscount(Long discountId){
        def discount = discountRepository.findById(discountId)

        if( discount.isEmpty() ){
            throw new ResourceNotFoundException("Discount not found")
        }
        discountRepository.deleteById(discountId)
    }
}
