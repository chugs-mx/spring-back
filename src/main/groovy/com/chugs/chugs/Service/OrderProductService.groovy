package com.chugs.chugs.Service

import com.chugs.chugs.entity.OrderProduct
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Product
import com.chugs.chugs.repository.OrderProductRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderProductService {
    @Autowired
    OrderProductRepository orderProductRepository

    @Autowired
    OrderTableRepository orderTableRepository

    @Autowired
    ProductRepository productRepository

    @Transactional
    OrderProduct addProductToOrder(Long orderId, Long productId, BigDecimal quantity){
        OrderTable orderTable = orderTableRepository.findById(orderId)
            .orElseThrow() -> new IllegalArgumentException("Order not found.")
        Product product = productRepository.findById(productId)
            .orElseThrow() -> new IllegalArgumentException("Product not found.")

        OrderProduct orderProduct = new OrderProduct()
        orderProduct.setOrder(orderTable)
        orderProduct.setProduct(product)
        orderProduct.setQuantity(quantity)
        orderProduct.setOrder_status(OrderProduct.OrderStatus.PREPARING)
        orderProduct.setNotified(false)

        return orderProductRepository.save(orderProduct)
    }

    @Transactional
    void deleteProductFromOrder(OrderTable order, Product product){
        OrderTable orderTable = orderTableRepository.findById(order.orderId)
            .orElseThrow() -> new IllegalArgumentException("OrderTable not found.")
        Product product1 = productRepository.findById(product.id)
            .orElseThrow() -> new IllegalArgumentException("Product not found.")
       OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(order, product)
            .orElseThrow() -> new IllegalArgumentException("OrderProduct not found.")

        orderProductRepository.delete(orderProduct)
    }

    @Transactional
    OrderProduct updateProductStatus(OrderTable order, Product product, OrderProduct.OrderStatus newStatus){
        OrderTable orderTable = orderTableRepository.findById(order.orderId)
            .orElseThrow() -> new IllegalArgumentException("OrderTable not found.")
        Product product1 = productRepository.findById(product.id)
            .orElseThrow() -> new IllegalArgumentException("Product not found.")
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(order, product)
            .orElseThrow() -> new IllegalArgumentException("OrderProduct not found. ")

        orderProduct.setOrder_status(newStatus)
        return orderProductRepository.save(orderProduct)
    }
}
