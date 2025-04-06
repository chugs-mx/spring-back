package com.chugs.chugs.repository

import com.chugs.chugs.entity.RestaurantTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long>{
}