package com.chugs.chugs.controller;

import com.chugs.chugs.Service.UserService;
import com.chugs.chugs.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public User createUser() {
        User user = new User();
        user.setName("Erick");
        user.setEmail("erickdjm@ellucia.com");
        user.setPasswordHash("ADHW@&da21273");
        user.setUserType(User.UserType.ADMIN);
        return userService.createUser(user);

        //check if it is not already created
        //required fields
        //format checks
        //password handling (hash the password before save it)


    }

    public UserService getUserService() {
        return userService;
    }

    private final UserService userService;
}
