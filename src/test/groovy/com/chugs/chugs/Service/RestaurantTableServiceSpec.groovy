package com.chugs.chugs.Service

import com.chugs.chugs.entity.RestaurantTable
import com.chugs.chugs.repository.RestaurantTableRepository
import spock.lang.Specification

class RestaurantTableServiceSpec extends Specification{

    RestaurantTableRepository restaurantTableRepository = Mock()

    RestaurantTableService restaurantTableService = new RestaurantTableService(restaurantTableRepository: restaurantTableRepository)

    def"test create new table successfully"(){
        given:
        RestaurantTable table = new RestaurantTable(tableId: null, seatNumber: 1, tableState: RestaurantTable.TableState.AVAILABLE)

        restaurantTableRepository.save(_) >> { args -> args[0] }

        when:
        RestaurantTable result = restaurantTableService.createNewTable(table)

        then:
        result.tableState == RestaurantTable.TableState.AVAILABLE
        result.seatNumber == table.seatNumber
    }

    def"test create new table fails if already exists"(){
        given:
        RestaurantTable tableExist = new RestaurantTable(tableId: 1L, seatNumber: 2, tableState: RestaurantTable.TableState.AVAILABLE)

        restaurantTableRepository.existsById(tableExist.tableId) >> Optional.of(tableExist)
        when:
        restaurantTableService.createNewTable(tableExist)

        then:
        thrown(IllegalArgumentException)
    }

    def"test update state table successfully"(){
        given:
        RestaurantTable table = new RestaurantTable(tableId: 1L, seatNumber: 2, tableState: RestaurantTable.TableState.AVAILABLE)

        restaurantTableRepository.findById(table.tableId) >> Optional.of(table)
        restaurantTableRepository.save(_ as RestaurantTable) >> { args -> args[0] }
        when:
        RestaurantTable res = restaurantTableService.updateState(table.tableId, RestaurantTable.TableState.RESERVED)

        then:
        res.tableState == RestaurantTable.TableState.RESERVED
    }

    def"test update seat number successfully"(){
        given:
        RestaurantTable table = new RestaurantTable(tableId: 1L, seatNumber: 2, tableState: RestaurantTable.TableState.AVAILABLE)

        restaurantTableRepository.findById(table.tableId) >> Optional.of(table)
        restaurantTableRepository.save(_ as RestaurantTable) >> {args -> args[0]}

        when:
        RestaurantTable res = restaurantTableService.updateSeatNumber(table.tableId, 4)

        then:
        res.seatNumber == 4
    }

    def "test delete table fails if not found"() {
        when:
        restaurantTableService.deleteTable(99L)

        then:
        thrown(IllegalArgumentException)
    }
}
