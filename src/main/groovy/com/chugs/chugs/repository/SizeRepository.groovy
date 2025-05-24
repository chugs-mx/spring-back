package com.chugs.chugs.repository

import com.chugs.chugs.entity.Size
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SizeRepository extends JpaRepository<Size, Long>{

}