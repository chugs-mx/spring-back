package com.chugs.chugs.controller

import com.chugs.chugs.Service.UserService
import com.chugs.chugs.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        //check if it is not already created
        //required fields
        //format checks
        //password handling (hash the password before save it)


    }
}
