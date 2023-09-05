package com.application.nothing.controller;

import com.application.nothing.model.User;
import com.application.nothing.service.UserService;
import com.application.nothing.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    // Fetch all users
    @Operation(summary = "Get All Users", description = "Fetches a list of all users registered in the system.")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Fetch a single user by ID
    @Operation(summary = "Get User by ID", description = "Fetches the details of a specific user identified by their ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @Operation(summary = "Create User", description = "Creates a new user account with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "User created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("requesting to create user");
        return ResponseEntity.ok(userService.createNewUser(user));
    }

    // Update a user by ID
    @Operation(summary = "Update User", description = "Updates the details of an existing user identified by their ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // You might want to do some checks or modifications here before saving
        return ResponseEntity.ok(userService.createNewUser(user));
    }

    // Delete a user by ID
    @Operation(summary = "Delete User", description = "Deletes a user account identified by its ID from the system.", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}