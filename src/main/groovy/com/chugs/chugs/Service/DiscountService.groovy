package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.repository.DiscountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

import java.time.LocalDate

@Service
class DiscountService {
    @Autowired
    DiscountRepository discountRepository

    // Obtain discount current
    List<Discount> getCurrentDiscounts(){
        LocalDate today = LocalDate.now()
        return discountRepository.findByStartDateBeforeAndEndDateAfter(today, today)
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
        return discountRepository.save(discount)
    }

    // Delete discount
    void deleteDiscount(Long discountId){
        if(! discountRepository.existsById(discountId)){
            throw new ResourceNotFoundException("Discount not found")
        }
        discountRepository.deleteById(discountId)
    }
}
