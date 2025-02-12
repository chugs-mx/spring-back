package com.chugs.chugs.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "app_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId
    @Column(nullable = false, length = 100)
    String name
    @Column(nullable = false, unique = true, length = 100)
    String email
    @Column(nullable = false, length = 255, name = "password_hash")
    String passwordHash
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_type")
    UserType userType
//    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<OrderTable> orders = new ArrayList<>()
    enum UserType {
        ADMIN, STAFF, WAITER, CUSTOMER
    }
}