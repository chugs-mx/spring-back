package com.chugs.chugs.repository

import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.RestaurantTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderTableRepository extends JpaRepository<OrderTable, Long>{
    List<OrderTable> findByTableTableId(Long tableId)
}