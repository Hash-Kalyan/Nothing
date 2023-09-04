package com.application.nothing.controller;

import com.application.nothing.model.User;
import com.application.nothing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping("/create")
    public ResponseEntity<ResponseEntity<String>> createUser(@RequestBody User user) {
        System.out.println("requesting to create user");
        return ResponseEntity.ok(userService.createNewUser(user));
    }


    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntity<String>> updateUser(@RequestBody User user) {
        // You might want to do some checks or modifications here before saving
        return ResponseEntity.ok(userService.updateUser(user));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
