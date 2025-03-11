package com.chugs.chugs.repository

import com.chugs.chugs.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByProductCategory(Product.Category category)

}