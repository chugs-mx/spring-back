package com.chugs.chugs.repository

import com.chugs.chugs.entity.ProductDefaultIngredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductDefaultIngredientRepository extends JpaRepository<ProductDefaultIngredient, Long>{

}