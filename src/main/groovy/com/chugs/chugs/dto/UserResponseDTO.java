package com.chugs.chugs.dto;

import com.chugs.chugs.entity.User.UserType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime birthDate;
    private UserType userType;
    private List<OrderTableDTO> orders = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<OrderTableDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderTableDTO> orders) {
        this.orders = orders != null ? orders : new ArrayList<>();
    }
}