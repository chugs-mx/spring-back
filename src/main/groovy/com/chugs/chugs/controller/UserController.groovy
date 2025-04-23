package com.chugs.chugs.controller

import com.chugs.chugs.Service.UserService
import com.chugs.chugs.dto.UserRequestDTO
import com.chugs.chugs.dto.UserResponseDTO
import com.chugs.chugs.entity.User
import com.chugs.chugs.mapper.UserMapper
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    UserService userService
    UserMapper userMapper

    @GetMapping("")
    Page<User> getPaginateUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue ="10", name = "size") int size) {
        logger.info("[Get] /user")
        userService.findAllUsers(page, size)
    }

    @PostMapping("")
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO)
        User createdUser = userService.createUser(user)
        UserResponseDTO userResponseDTO = userMapper.toDto(createdUser)
        logger.info("[Post] /user")
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED)
    }

    @GetMapping("/valid")
    User getValidUser(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        Optional<User> u =   userService.getValidUser(email, password)
        if (u.isPresent()) {
            return u.get()
        } else {
            return null
        }
    }
}
