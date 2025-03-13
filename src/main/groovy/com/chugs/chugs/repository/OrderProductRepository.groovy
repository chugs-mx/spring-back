package com.chugs.chugs.repository

import com.chugs.chugs.entity.OrderProduct
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
    List<OrderProduct> findByOrder(OrderTable order)
    Optional<OrderProduct> findByOrderAndProduct(OrderTable order, Product product)

}