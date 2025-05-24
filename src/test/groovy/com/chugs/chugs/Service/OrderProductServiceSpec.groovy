package com.chugs.chugs.Service

import com.chugs.chugs.entity.OrderProduct
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Product
import com.chugs.chugs.repository.OrderProductRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.ProductRepository
import spock.lang.Specification

class OrderProductServiceSpec extends Specification{
    OrderProductRepository orderProductRepository = Mock()
    OrderTableRepository orderTableRepository = Mock()
    ProductRepository productRepository = Mock()

    OrderProductService orderProductService = new OrderProductService(orderProductRepository: orderProductRepository, orderTableRepository: orderTableRepository, productRepository: productRepository)

    def"testAddProductToOrder"(){
        given:
        Long productId = 1L
        Long orderId = 2L
        BigDecimal quantity = BigDecimal.valueOf(2)

        OrderTable orderTable = new OrderTable(orderId: orderId)
        Product product = new Product(productId: productId, name: "Burger", description: "Burger", category: Product.Category.HAMBURGERS, price: BigDecimal.valueOf(120))

        orderTableRepository.findById(orderId) >> Optional.of(orderTable)
        productRepository.findById(productId) >> Optional.of(product)
        orderProductRepository.save(_ as OrderProduct) >> { args -> args[0] }

        when:
        OrderProduct result = orderProductService.addProductToOrder(orderId, productId, quantity)

        then:
        result.order == orderTable
        result.product == product
        result.quantity == quantity
        result.order_status == OrderProduct.OrderStatus.PREPARING
        result.notified == false
    }

    def"testOrdenNotFoundWhenAddProductToOrder"(){
        given:
        Long orderId = 1L
        Long productId = 2L
        BigDecimal quantity = BigDecimal.valueOf(2)

        orderTableRepository.findById(orderId) >> Optional.empty()

        when:
        orderProductService.addProductToOrder(orderId, productId, quantity)

        then:
        thrown(IllegalArgumentException)
    }

    def"testProductNotFoundWhenAddProductToOrder"(){
        given:
        Long orderId = 1L
        Long productId = 2L
        BigDecimal quantity = BigDecimal.valueOf(2)

        OrderTable order = new OrderTable(orderId: orderId)

        orderTableRepository.findById(orderId) >> Optional.of(order)
        productRepository.findById(productId) >> Optional.empty()

        when:
        orderProductService.addProductToOrder(orderId, productId, quantity)

        then:
        thrown(IllegalArgumentException)
    }

    def"testDeleteProduct"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)
        Product product = new Product(productId: 2L)
        OrderProduct orderProduct = new OrderProduct(order: order, product: product)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)
        productRepository.findById(product.productId) >> Optional.of(product)
        orderProductRepository.findByOrderAndProduct(order, product) >> Optional.of(orderProduct)

        when:
        orderProductService.deleteProductFromOrder(order, product)

        then:
        1 * orderProductRepository.delete(orderProduct)
    }

    def"test DeleteProduct Should Throw Exception When Order Not Found"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)
        Product product = new Product(productId: 2L)

        orderTableRepository.findById(order.orderId) >> Optional.empty()

        when:
        orderProductService.deleteProductFromOrder(order, product)

        then:
        thrown(IllegalArgumentException)
    }

    def"updateProductStatus successfully"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)
        Product product = new Product(productId: 2L)
        OrderProduct orderProduct = new OrderProduct(order: order, product: product)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)
        productRepository.findById(product.productId) >> Optional.of(product)
        orderProductRepository.findByOrderAndProduct(order, product) >> Optional.of(orderProduct)
        orderProductRepository.save(_ as OrderProduct) >> { args -> args[0] }

        when:
        OrderProduct result = orderProductService.updateProductStatus(order, product, OrderProduct.OrderStatus.READY)

        then:
        result.order_status == OrderProduct.OrderStatus.READY
    }

    def"updateProductStatus should throw exception if OrderProduct not found"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)
        Product product = new Product(productId: 2L)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)
        productRepository.findById(product.productId) >> Optional.of(product)
        orderProductRepository.findByOrderAndProduct(order, product) >> Optional.empty()

        when:
        orderProductService.updateProductStatus(order, product, OrderProduct.OrderStatus.READY)

        then:
        thrown(IllegalArgumentException)
    }
}
