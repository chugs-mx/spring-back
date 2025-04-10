package com.chugs.chugs.controller;

import com.chugs.chugs.Service.UserService;
import com.chugs.chugs.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class Auth {

    final
    UserService userService;

    public Auth(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            User foundUser = user.get();
            if (foundUser.getPasswordHash().equals(password)) {
                return ResponseEntity.ok(foundUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
