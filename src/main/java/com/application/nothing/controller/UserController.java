package com.application.nothing.controller;

import com.application.nothing.dto.UserDTO;
import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        logger.info("Creating new user");
        return ResponseEntity.ok(userService.save(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        logger.info("Updating user with ID: {}", id);
        userDTO.setUserId(id);
        return ResponseEntity.ok(userService.save(userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String email, @RequestParam String password) {
        logger.info("User login attempt with email: {}", email);
        return ResponseEntity.ok(userService.login(email, password));
    }
}



