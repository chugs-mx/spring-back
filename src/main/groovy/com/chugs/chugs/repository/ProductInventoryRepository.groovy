package com.chugs.chugs.repository

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.entity.Product
import com.chugs.chugs.entity.ProductInventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long>{
    List<ProductInventory> findByProductId(Product product) // to obtain all the ingredients of a product
    Optional<ProductInventory> findByProductIdAndInventory(Product product, Inventory inventory) //relationship between a product and a ingredient

}