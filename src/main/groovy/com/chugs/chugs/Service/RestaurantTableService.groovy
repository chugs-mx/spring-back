package com.chugs.chugs.Service

import com.chugs.chugs.entity.RestaurantTable
import com.chugs.chugs.repository.RestaurantTableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RestaurantTableService {
    @Autowired
    RestaurantTableRepository restaurantTableRepository

    RestaurantTable createNewTable(RestaurantTable table){
       if( table.tableId != null && restaurantTableRepository.existsById(table.tableId)){
           throw new IllegalArgumentException("Table already exists.")
       }
        validateRestaurantTable(table)
        return restaurantTableRepository.save(table)
    }

    RestaurantTable updateState(Long tableId, RestaurantTable.TableState tableState){
        RestaurantTable table = restaurantTableRepository.findById(tableId)
            .orElseThrow() -> new IllegalArgumentException("Table not found.")

        table.setTableState(tableState)
        return restaurantTableRepository.save(table)
    }

    RestaurantTable updateSeatNumber(Long tableId, int newSeatNumber){
        if( newSeatNumber <= 0 ){
            throw new IllegalArgumentException("Seat number must be greater than zero.")
        }
        RestaurantTable table = restaurantTableRepository.findById(tableId)
            .orElseThrow() -> new IllegalArgumentException("Table not found.")

        table.setSeatNumber(newSeatNumber)
        return restaurantTableRepository.save(table)
    }

    void deleteTable(Long tableId){
        if( !restaurantTableRepository.existsById(tableId) ){
            throw new IllegalArgumentException("Table not found.")
        }
        restaurantTableRepository.deleteById(tableId)
    }

    void validateRestaurantTable(RestaurantTable table){
        if( table.seatNumber == null ){
            throw new IllegalArgumentException("Seat number is required.")
        }
        if( table.tableState == null ){
            throw new IllegalArgumentException("Table state is required.")
        }
    }
}
