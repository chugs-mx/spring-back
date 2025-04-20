package com.chugs.chugs.controller

import com.chugs.chugs.Service.UserService
import com.chugs.chugs.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

@RestController
class UserController {

    @Autowired
    UserService userService

    @GetMapping("/user")
    Page<User> getPaginateUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue ="10", name = "size") int size) {
        userService.findAllUsers(page, size)
    }

    @PostMapping("/user")
    User createUser() {
        User user = new User();
        user.name = "Erick"
        user.email = "erick@gmail.com"
        user.passwordHash = "pass"
        user.userType = User.UserType.ADMIN
        user.birthDate = LocalDateTime.now()
        userService.createUser(user)

        //check if it is not already created
        //required fields
        //format checks
        //password handling (hash the password before save it)
    }

    @GetMapping("/user/valid")
    User getValidUser(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        Optional<User> u =   userService.getValidUser(email, password)
        if (u.isPresent()) {
            return u.get()
        } else {
            return null
        }
    }
}
