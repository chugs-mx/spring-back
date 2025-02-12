package com.chugs.chugs.Service

import com.chugs.chugs.repository.UserRepository
import com.chugs.chugs.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    UserRepository userRepository

    Page<User> findAllUsers(int page, int size) {


        Pageable pageable = PageRequest.of(page, size)
        return userRepository.findAll(pageable)
    }
    // Create a new user
    User createUser(User user) {
        return userRepository.save(user)
    }

    // Find a user by email
    Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
    }

    // Find a user by name
    Optional<User> findUserByName(String name) {
        return userRepository.findByEmail(name)
    }

    // Get all users
    List<User> getAllUsers() {
        return userRepository.findAll()
    }

    // Delete user by ID
    void deleteUser(Long id) {
        userRepository.deleteById(id)
    }
}
