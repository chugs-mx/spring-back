package com.chugs.chugs.repository

import com.chugs.chugs.entity.Subcategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubcategoryRepository extends JpaRepository<Subcategory, Long>{

}