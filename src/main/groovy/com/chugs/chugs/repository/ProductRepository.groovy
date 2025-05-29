package com.chugs.chugs.repository

import com.chugs.chugs.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   // List<Product> findByProductCategory(Product.Category category)
    Page<Product> findAll(Specification<Product> specification, Pageable pageable)

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.subcategory")
    List<Product> findAllWithCategoryAndSubcategory();

    @Query("""
    SELECT DISTINCT p FROM Product p
    JOIN FETCH p.category
    JOIN FETCH p.subcategory
    LEFT JOIN FETCH p.defaultIngredients di
    LEFT JOIN FETCH di.ingredient
    LEFT JOIN FETCH di.size
""")
    List<Product> findAllWithFullData();


}