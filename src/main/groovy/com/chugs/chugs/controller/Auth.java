package com.chugs.chugs.controller;

import com.chugs.chugs.Service.UserService;
import com.chugs.chugs.dto.UserResponseDTO;
import com.chugs.chugs.entity.User;
import com.chugs.chugs.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class Auth {

    final
    UserService userService;
    private final UserMapper userMapper;

    public Auth(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            User foundUser = user.get();
            if (foundUser.getPasswordHash().equals(password)) {
                UserResponseDTO userResponseDTO = userMapper.toDto(foundUser);
                return ResponseEntity.ok(userResponseDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
