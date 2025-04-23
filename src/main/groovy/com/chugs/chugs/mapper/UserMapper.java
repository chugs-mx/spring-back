package com.chugs.chugs.mapper;
import com.chugs.chugs.dto.OrderTableDTO;
import com.chugs.chugs.dto.UserRequestDTO;
import com.chugs.chugs.dto.UserResponseDTO;
import com.chugs.chugs.entity.OrderTable;
import com.chugs.chugs.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setBirthDate(dto.getBirthDate());
        user.setUserType(dto.getUserType());
        return user;
    }

    public UserResponseDTO toDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setUserType(user.getUserType());

        // Map orders
        if (user.getOrders() != null) {
            dto.setOrders(user.getOrders().stream()
                    .map(this::toOrderDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }


    private OrderTableDTO toOrderDto(OrderTable order) {
        OrderTableDTO dto = new OrderTableDTO();
        dto.setOrderId(order.getOrderId());
        dto.setTotal(order.getTotal());
        dto.setOrderDate(order.getOrderDate());
        dto.setPartySize(order.getPartySize());
        dto.setUserId(order.getUser().getUserId());
        dto.setTableId(order.getTable().getTableId());
        return dto;
    }
}