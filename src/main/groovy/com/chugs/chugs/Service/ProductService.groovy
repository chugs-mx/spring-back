package com.chugs.chugs.Service

import com.chugs.chugs.dto.ProductRequestDTO
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

import javax.swing.text.html.Option

@Service
class ProductService {

    @Autowired
    ProductRepository productRepository

    public static final Logger logger = LoggerFactory.getLogger(this.class);

    public Product createProductDTO(ProductRequestDTO dto){

    }


    Product updateProduct(Long id, Product product){
        Optional<Product> optionalExistingProduct = productRepository.findById(id)

        if( optionalExistingProduct.isEmpty() ){
            logger.info("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        }else{
            logger.info("Product found with id: $id")
        }

        Product exisitingProduct = optionalExistingProduct.get()

        if( product.name ){
            exisitingProduct.name = product.name
        }
        if( product.description = product.description ){
            exisitingProduct.description = product.description
        }
        if( product.price ){
            exisitingProduct.price = product.price
        }
        if( product.productCategory ){
            exisitingProduct.productCategory = product.productCategory
        }
        return productRepository.save(exisitingProduct)
    }


    Product deleteProduct(Long id){
        Optional<Product> optionalExistingProduct = productRepository.findById(id)
        if( optionalExistingProduct.isPresent() ){
            Product product = optionalExistingProduct.get()
            productRepository.delete(product)
            return product
        }else {
            logger.error("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        }
    }

    List<Product> getAllProducts(){
        return productRepository.findAll()
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

    Product patchProduct(Long id, Product product){
        Optional<Product> optionalExistingProduct = productRepository.findById(id);
        if( optionalExistingProduct.isEmpty() ){
            logger.info("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        }else{
            logger.info("Product found with id: $id")
        }

        Product existingProduct = optionalExistingProduct.get()
        if( product.name ){
            existingProduct.name = product.name
        }
        if( product.description ){
            existingProduct.description = product.description
        }
        if( product.price ){
            existingProduct.price = product.price
        }
        if( product.productCategory ){
            existingProduct.productCategory = product.productCategory
        }
        return productRepository.save(existingProduct)
    }


}
