package com.chugs.chugs.Service

import com.chugs.chugs.entity.Product
import com.chugs.chugs.repository.ProductRepository
import com.chugs.chugs.repository.specification.ProductSpecification
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService {

    @Autowired
    ProductRepository productRepository

    public static final Logger logger = LoggerFactory.getLogger(this.class);

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
        }) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found."))
    }

    @Transactional
    void deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found."))
        productRepository.delete(product)
    }

    List<Product> getAllProducts(){
        return productRepository.findAll()
    }

    List<Product> getProductsByCategory(Product.Category category){
        return productRepository.findByProductCategory(category)
    }

    static void validateProduct(Product product){
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

    Page<Product> getProductsWithPaginationFilteringAndSorting(int page, int size, String search, String sort, boolean asc, String category){
        Sort sortOrder = sort ? ( asc ? Sort.by(sort).ascending() : Sort.by(sort).descending() ) : Sort.unsorted()
        PageRequest pageRequest = PageRequest.of(page, size, sortOrder)
        Specification<Product> spec = ProductSpecification.getSpecification(search, category);
        Page<Product> productPage = productRepository.findAll(spec, pageRequest);
        return productPage;
    }

    Product getProduct(long id){
        Optional<Product> optionalProduct = productRepository.findById(id)
        if( optionalProduct.isEmpty() ){
            logger.info("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        }else {
            return optionalProduct.get()
        }
    }

}
