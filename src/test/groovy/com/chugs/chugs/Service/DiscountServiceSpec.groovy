package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.repository.DiscountRepository
import spock.lang.*
import java.time.LocalDateTime

class DiscountServiceSpec extends Specification{
    DiscountRepository discountRepository = Mock()
    DiscountService discountService = new DiscountService(discountRepository: discountRepository)

    def "Test obtain discount current"(){
        given:
        LocalDateTime today = LocalDateTime.of(2025, 2, 25, 12, 0)
        println "today: ${today}"

        // Discounts test
        List<Discount> discounts =[
                new Discount(name: "CHUGS", startDate: LocalDateTime.of(2025, 3, 25, 12, 0), endDate: LocalDateTime.of(2025, 4, 25, 12, 0)),
                new Discount(name: "OTRO", startDate: LocalDateTime.of(2025, 1, 1, 13, 0), endDate: LocalDateTime.of(2025, 4, 2, 12, 0)),
                new Discount(name: "DESC", startDate: LocalDateTime.of(2024, 12, 3, 5, 0), endDate: LocalDateTime.of(2024, 12, 4, 5,0))
        ]

        println "startDay: ${discounts[0].startDate}"
        println "endDay: ${discounts[0].endDate}"

        discountRepository.findByStartDateBeforeAndEndDateAfter(today, today) >> {
            println "Mocking repository call"
            return discounts
        }
        when:
        List<Discount> currentDiscounts = discountService.getCurrentDiscounts(today)

        then:
        currentDiscounts.size() == 1
        currentDiscounts[0].name == "OTRO"
    }

    // Test for obtain discount by name
    def"testObtainDiscountByName"(){
        given:
        String findName = "CHUGS"

        List<Discount> discounts = [
                new Discount(name: "CHUGS"),
                new Discount(name: "CHUGS"),
                new Discount(name: "PRUEBA")
        ]

        discountRepository.findByName(findName) >> Optional.of(discounts.find { it.name == findName })

        when:
        Discount discount = discountService.getDiscountByName(findName)

        then:
        discount.name == findName

    }
}
