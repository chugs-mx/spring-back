package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.repository.DiscountRepository
import spock.lang.*
import java.time.LocalDate


import java.time.LocalDate

class DiscountServiceSpec extends Specification{
    DiscountRepository discountRepository = Mock(DiscountRepository)
    DiscountService discountService = new DiscountService(discountRepository: discountRepository)

    def "getCurrentDiscounts returns discounts valid for today"() {
        given:
        LocalDate today = LocalDate.now()
        List<Discount> discounts = [ new Discount(name: "CHUGS", startDate: today.minusDays(1), endDate: today.plusDays(2))]
        discountRepository.findByStartDateBeforeAndEndDateAfter(today, today) >> discounts

        when:
        List<Discount> currentDiscounts = discountService.getCurrentDiscounts()

        then:
        currentDiscounts == discounts
    }
}
