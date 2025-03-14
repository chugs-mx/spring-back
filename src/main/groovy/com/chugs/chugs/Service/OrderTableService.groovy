package com.chugs.chugs.Service

import com.chugs.chugs.entity.OrderProduct
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.RestaurantTable
import com.chugs.chugs.entity.User
import com.chugs.chugs.repository.OrderProductRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.RestaurantTableRepository
import com.chugs.chugs.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class OrderTableService {
    @Autowired
    OrderTableRepository orderTableRepository

    @Autowired
    OrderProductRepository orderProductRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    RestaurantTableRepository restaurantTableRepository

    @Transactional
    OrderTable createOrder(Long userId, Long tableId, Integer partySize){
        User user = userRepository.findById(userId)
            .orElseThrow() -> new IllegalArgumentException("User not found.")
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
            .orElseThrow() -> new IllegalArgumentException("Table not found.")

        OrderTable orderTable = new OrderTable()
        orderTable.setUser(user)
        orderTable.setTable(restaurantTable)
        orderTable.setPartySize(partySize)
        orderTable.setOrderDate(LocalDateTime.now())
        orderTable.setTotal(BigDecimal.ZERO)

        return orderTableRepository.save(orderTable)
    }

    @Transactional
    OrderTable updatePartySize(Long orderId, Integer newPartySize){
        OrderTable order = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")

        order.setPartySize(newPartySize)
        return orderTableRepository.save(order)
    }

    @Transactional
    OrderTable changeTable(Long orderId, Long restaurantTableId){
        OrderTable order = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")
        RestaurantTable newTable = restaurantTableRepository.findById(restaurantTableId)
            .orElseThrow() -> new IllegalArgumentException("Restaurant table not found.")

        order.setTable(newTable)
        return orderTableRepository.save(order)
    }

    @Transactional
    OrderTable changeUser(Long orderId, Long userId){
        OrderTable order = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")
        User newUser = userRepository.findById(userId)
            .orElseThrow() -> new IllegalArgumentException("User not found.")

        order.setUser(newUser)
        return orderTableRepository.save(order)
    }

    List<OrderProduct> getProductsByOrder(Long orderId){
        OrderTable order = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")

        return orderProductRepository.findByOrder(order)
    }

    @Transactional
    OrderTable updateTotal(Long orderId){
        OrderTable order = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")

        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order)

        BigDecimal subtotal = orderProducts.stream()
                .map(op -> op.product.price.multiply(op.quantity))
                .reduce(BigDecimal.ZERO, BigDecimal::add)

        BigDecimal total = subtotal
        order.setTotal(total)

        return orderTableRepository.save(order)
    }


    @Transactional
    void cancelOrder(Long orderId){
        OrderTable order = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")
        orderTableRepository.delete(order)
    }
}
