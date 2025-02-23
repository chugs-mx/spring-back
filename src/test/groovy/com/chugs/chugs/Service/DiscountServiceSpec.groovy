package com.chugs.chugs.Service

import com.chugs.chugs.entity.Discount
import com.chugs.chugs.repository.DiscountRepository
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.mockito.Mock
import org.springframework.data.jpa.domain.Specification
import org.springframework.lang.Nullable

import java.time.LocalDate

class DiscountServiceSpec implements Specification {
    DiscountRepository discountRepository = Mock()
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

    @Override
    Predicate toPredicate(Root root, @Nullable CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        return null
    }
}
