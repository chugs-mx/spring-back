package com.chugs.chugs.repository

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.entity.Product
import com.chugs.chugs.entity.ProductInventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long>{
    List<ProductInventory> findByProduct(Product product) // to obtain all the ingredients of a product
    List<ProductInventory> findByInventory(Inventory inventory) // to obtain all products using an input
    Optional<ProductInventory> findByProductAndInventory(Product product, Inventory inventory) //relationship between a product and a ingredient

}