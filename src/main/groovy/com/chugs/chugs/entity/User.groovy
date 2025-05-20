package com.chugs.chugs.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long userId

    @Column(nullable = false, length = 100)
    String name

    @Column(nullable = false, unique = true, length = 100)
    String email

    @Column(nullable = false, length = 255, name = "password_hash")
    String passwordHash

    @Column(name = "birth_date", nullable = false)
    LocalDateTime birthDate

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_type")
    UserType userType

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderTable> orders = []

    enum UserType {
        ADMIN, STAFF, WAITER, CUSTOMER
    }
}