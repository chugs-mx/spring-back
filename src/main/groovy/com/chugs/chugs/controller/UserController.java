package com.chugs.chugs.controller;

import com.chugs.chugs.Service.UserService;
import com.chugs.chugs.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Page<User> getPaginateUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return userService.findAllUsers(page, size);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setPasswordHash(user.getPasswordHash() );
        user.setUserType(user.getUserType());
        return userService.createUser(user);

        //check if it is not already created
        //required fields
        //format checks
        //password handling (hash the password before save it)


    }

    private final UserService userService;
}
