package com.chugs.chugs.repository

import com.chugs.chugs.dto.CategoryDTO
import com.chugs.chugs.dto.ProductRequestDTO
import com.chugs.chugs.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository extends JpaRepository<Category, Long>{
}