package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.dtos.SafeUserDTO;
import com.tkosmulski.yetAnotherLibrarySystem.models.BaseUser;
import com.tkosmulski.yetAnotherLibrarySystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<SafeUserDTO>> getUsers() {
        logger.info("Request fot getting all users.");
        return new ResponseEntity<>(SafeUserDTO.from(userService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SafeUserDTO> getUserById(@PathVariable Long id) {
        logger.info(String.format("Request for getting user with id %d.", id));
        return new ResponseEntity<>(new SafeUserDTO(userService.findByIdOrThrow(id)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<SafeUserDTO> registerUser(@RequestBody BaseUser user) {
        logger.info("Request for adding user.");
        return new ResponseEntity<>(new SafeUserDTO(userService.add(user)), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<SafeUserDTO> edit(@RequestBody BaseUser user) {
        logger.info(String.format("Request for editing user with id %d.", user.getId()));
        return new ResponseEntity<>(new SafeUserDTO(userService.edit(user)),HttpStatus.OK);
    }
}
