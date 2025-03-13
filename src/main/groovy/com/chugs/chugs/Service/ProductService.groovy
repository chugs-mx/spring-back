package com.chugs.chugs.Service

import com.chugs.chugs.entity.Product
import com.chugs.chugs.repository.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductService {

    @Autowired
    ProductRepository productRepository

    Product createProduct(Product product){
        validateProduct(product)
        return productRepository.save(product)
    }

    @Transactional
    Product updateProduct(Long productId, Product updateProduct){
        return productRepository.findById(productId).map (product -> {
            validateProduct(updateProduct)
            product.setName(updateProduct.name)
            product.setDescription(updateProduct.description)
            product.setProductCategory(updateProduct.productCategory)
            product.setPrice(updateProduct.price)
            return productRepository.save(product)
        }) .orElseThrow(() -> new ResourceNotFoundException("Product not found."))
    }

    @Transactional
    void deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."))
        productRepository.delete(product)
    }

    List<Product> getAllProducts(){
        return productRepository.findAll()
    }

    List<Product> getProductsByCategory(Product.Category category){
        return productRepository.findByProductCategory(category)
    }

    void validateProduct(Product product){
        if( product.name == null || product.name.trim().isEmpty() || product.name.length() > 100 ){
            throw new IllegalArgumentException("Product name is required.")
        }
        if( product.description == null || product.description.trim().isEmpty() || product.description.length() > 500 ){
            throw new IllegalArgumentException("Description is required.")
        }
        if( product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The price cannot be less than 0.")
        }
        if( product.productCategory == null ){
            throw new IllegalArgumentException("The category is required.")
        }
    }


}
