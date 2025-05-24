package com.chugs.chugs.Service

import com.chugs.chugs.entity.Product
import com.chugs.chugs.repository.ProductRepository
import spock.lang.Specification

class ProductServiceSpec extends Specification{

    ProductRepository productRepository = Mock()

    ProductService productService = new ProductService(productRepository: productRepository)

    def"testCreateProduct"(){
        given:
        Product product = new Product(name: "Vegan hamburger", description: "Vegan hamburger", category: Product.Category.HAMBURGERS, price: BigDecimal.valueOf(120))

        productRepository.save(product) >> product
        when:
        Product productSaved = productService.createProduct(product)

        then:
        productSaved != null
        productSaved.name == product.name
        productSaved.category == product.category
    }

    def"testCreateErrorWithoutDataRequirement"(){
        given:
        Product product = new Product(name: badName, description: badDescription, category: badCategory, price: badPrice)

        when:
        productService.createProduct(product)

        then:
        def e = thrown(IllegalArgumentException)
        assert e.message in ["Product name is required.", "Description is require.", "The category is required.", "The price cannot be less than 0."]

        where:
        badName | badDescription | badCategory | badPrice
        null | null | null | null
        null | null | null | -1
    }

    def"testUpdateProduct"(){
        given:
        Long productId = 1L
        Product existingProduct = new Product(productId: productId, name: "Hamburger", description: "Deli hamburguer", category: Product.Category.HAMBURGERS, price: BigDecimal.valueOf(120) )
        Product updateProduct = new Product(productId: productId, name: "Burger", description: "Burger", category: Product.Category.HAMBURGERS, price: BigDecimal.valueOf(120))

        productRepository.findById(productId) >> Optional.of(existingProduct)
        productRepository.save(_ as Product) >> updateProduct

        when:

        Product result = productService.updateProduct(productId, updateProduct)

        then:
        result != null
        result.name == "Burger"
        result.description == "Burger"
    }

    def"testDeleteProduct"(){
        given:
        Long productId = 1L
        Product product = new Product(productId: productId, name: "Burger", description: "Burger", category: Product.Category.HAMBURGERS, price: BigDecimal.valueOf(120))
        productRepository.findById(productId) >> Optional.of(product)

        when:
        productService.deleteProduct(productId)

        then:
        1 * productRepository.delete(product)
    }

    def "testGetAll"(){
        given:
        List<Product> products = [
                new Product(productId: 1L, name: "Milkshake", description: "Cramel Milkshake", category: Product.Category.DRINKS, price: BigDecimal.valueOf(70)),
                new Product(productId: 2L, name: "Burger", description: "Burger", category: Product.Category.HAMBURGERS, price: BigDecimal.valueOf(100))
        ]

        productRepository.findAll() >> products
        when:
        List<Product> allProducts = productService.getAllProducts()

        then:
        allProducts.size() == 2
        allProducts[0].name == "Milkshake"
    }

    def"testGetByCategory"(){
        given:
        def category = Product.Category.HAMBURGERS
        List<Product> hamburgers = [
                new Product(productId: 1L, name: "Burger", description: "Burger", category: category, price: BigDecimal.valueOf(120)),
                new Product(productId: 2L, name: "Cheese Burger", description: "Burger", category: category, price: BigDecimal.valueOf(30))
        ]

        productRepository.findByProductCategory(category) >> hamburgers

        when:
       List<Product> productsByCategory = productService.getProductsByCategory(category)

        then:
        productsByCategory.size() == 2
        productsByCategory[0].name == "Burger"
        productsByCategory[1].category == category
    }


}
