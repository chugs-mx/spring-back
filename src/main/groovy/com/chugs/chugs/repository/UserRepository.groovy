package com.chugs.chugs.repository

import com.chugs.chugs.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email)
    Optional<User> findByName(String name)
}