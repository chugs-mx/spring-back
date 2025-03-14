package com.chugs.chugs.Service

import com.chugs.chugs.entity.OrderProduct
import com.chugs.chugs.entity.OrderTable
import com.chugs.chugs.entity.Product
import com.chugs.chugs.entity.RestaurantTable
import com.chugs.chugs.entity.User
import com.chugs.chugs.repository.OrderProductRepository
import com.chugs.chugs.repository.OrderTableRepository
import com.chugs.chugs.repository.RestaurantTableRepository
import com.chugs.chugs.repository.UserRepository
import spock.lang.Specification

class OrderTableServiceSpec extends Specification{
    OrderProductRepository orderProductRepository = Mock()
    UserRepository userRepository = Mock()
    OrderTableRepository orderTableRepository = Mock()
    RestaurantTableRepository restaurantTableRepository = Mock()

    OrderTableService orderTableService = new OrderTableService(
            orderProductRepository: orderProductRepository,
            userRepository: userRepository,
            restaurantTableRepository: restaurantTableRepository,
            orderTableRepository: orderTableRepository)

    def"test create order successful"(){
        given:
        Integer partySize = 2
        User user = new User(userId: 1L)
        RestaurantTable table = new RestaurantTable(tableId: 1L)

        userRepository.findById(user.userId) >> Optional.of(user)
        restaurantTableRepository.findById(table.tableId) >> Optional.of(table)

        orderTableRepository.save(_) >> {args -> args[0] }
        when:
        OrderTable orderCreate = orderTableService.createOrder(1L, 1L, partySize)

        then:
        orderCreate.user == user
        orderCreate.table == table
        orderCreate.partySize == partySize
        orderCreate.total == BigDecimal.ZERO
    }

    def"test update party size"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L, partySize: 2)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)
        orderTableRepository.save(_) >> {args -> args[0]}

        when:
        OrderTable updatedOrder = orderTableService.updatePartySize(order.orderId, 5)

        then:
        updatedOrder.partySize == 5
    }

    def"test change table"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)
        RestaurantTable newTable = new RestaurantTable(tableId: 2L)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)
        restaurantTableRepository.findById(newTable.tableId) >> Optional.of(newTable)
        orderTableRepository.save(_) >> {args -> args[0]}

        when:
        OrderTable updatedOrder = orderTableService.changeTable(1L, 2L)

        then:
        updatedOrder.table == newTable
    }

    def"test change user"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L, user: new User(userId: 1L))
        User newUser = new User(userId: 2L)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)
        userRepository.findById(newUser.userId) >> Optional.of(newUser)
        orderTableRepository.save(_) >> {args -> args[0]}

        when:
        OrderTable updatedOrder = orderTableService.changeUser(order.orderId, newUser.userId)

        then:
        updatedOrder.user == newUser
    }

    def"get products by order"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)
        Product product = new Product(productId: 1L, price: new BigDecimal("10.00"))
        Product product1 = new Product(productId: 2L, price: new BigDecimal("10.00"))

        OrderProduct orderProduct = new OrderProduct(order: order, product: product, quantity: new BigDecimal("2"))
        OrderProduct orderProduct1 = new OrderProduct(order: order, product: product1, quantity: new BigDecimal("2"))

        orderTableRepository.findById(1L) >> Optional.of(order)
        orderProductRepository.findByOrder(order) >> [orderProduct, orderProduct1]

        when:
        List<Product> products = orderTableService.getProductsByOrder(1L)

        then:
        products.size() == 2
        products[0] == orderProduct
    }

    def"testUpdateTotal"(){
        given:
        OrderTable orderTable = new OrderTable(orderId: 1L)
        Product product = new Product(price: new BigDecimal("10.00"))
        OrderProduct orderProduct = new OrderProduct(order: orderTable, product: product, quantity: BigDecimal.valueOf(2))

        orderTableRepository.findById(orderTable.orderId) >> Optional.of(orderTable)
        orderProductRepository.findByOrder(orderTable) >> [orderProduct]
        orderTableRepository.save(_) >> { args -> args[0] }

        when:
        OrderTable updatedOrder = orderTableService.updateTotal(orderTable.orderId)

        then:
        updatedOrder.total == new BigDecimal("20.00")
    }

    def"test cancel order"(){
        given:
        OrderTable order = new OrderTable(orderId: 1L)

        orderTableRepository.findById(order.orderId) >> Optional.of(order)

        when:
        orderTableService.cancelOrder(order.orderId)

        then:
        1 * orderTableRepository.delete(order)
    }

}
