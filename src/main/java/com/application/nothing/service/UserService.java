package com.application.nothing.service;

import com.application.nothing.dto.UserDTO;
import com.application.nothing.exception.UserAlreadyExistsException;
import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.model.User;
import com.application.nothing.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertEntityToDTO); // convertToDTO is a method that converts User to UserDTO
    }


    //    public UserDTO save(UserDTO userDTO) {
//        if (userRepository.existsByEmail(userDTO.getEmail())) {
//            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
//        }
//        User user = convertToEntity(userDTO);
//        User savedUser = userRepository.save(user);
//        return convertToDTO(savedUser);
//    }
    public UserDTO save(UserDTO userDTO) {
        // Check if a user with the same userName, email, or phone already exists
        userRepository.findByUsername(userDTO.getUsername()).ifPresent(existingUser -> {
            if (!existingUser.getUserId().equals(userDTO.getUserId())) {
                throw new UserAlreadyExistsException("A user with this username already exists");
            }
        });
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getUserId().equals(userDTO.getUserId())) {
                throw new UserAlreadyExistsException("A user with this email already exists");
            }
        });
        userRepository.findByPhone(userDTO.getPhone()).ifPresent(existingUser -> {
            if (!existingUser.getUserId().equals(userDTO.getUserId())) {
                throw new UserAlreadyExistsException("A user with this phone number already exists");
            }
        });

        // Convert DTO to entity and save it
        User user = convertDTOToEntity(userDTO);
        User savedUser = userRepository.save(user);

        // Convert saved entity back to DTO and return it
        return convertEntityToDTO(savedUser);
    }


    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

//    private UserDTO convertToDTO(User user) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId(user.getUserId());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setEmail(user.getEmail());
//        // ... (other fields)
//        return userDTO;
//    }
//
//    private User convertToEntity(UserDTO userDTO) {
//        User user = new User();
//        user.setUserId(userDTO.getUserId());
//        user.setUsername(userDTO.getUsername());
//        user.setEmail(userDTO.getEmail());
//        // ... (other fields)
//        return user;
//    }

    public User convertDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        // ... (other fields)
        return user;
    }

    private UserDTO convertEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        // ... (other fields)
        return userDTO;
    }
}



//package com.application.nothing.service;
//
//import com.application.nothing.model.User;
//import com.application.nothing.exception.UserNotFoundException;
//import com.application.nothing.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//import java.lang.String;
//
//@Service
//public class UserService {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    // Any other additional methods or business logic can go here.
//    @Transactional(readOnly = true)
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<User> findById(Long id) {
//        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
//                new UserNotFoundException("NO USER PRESENT WITH ID = " + id)));
//    }
//
//    @Transactional
//    public ResponseEntity<String> createNewUser(User user) {
//        try {
//            if (userRepository.findByUsername(user.getUsername()) != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
//            }
//            if (userRepository.findByEmail(user.getEmail()) != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
//            }
//            if (userRepository.findByPhone(user.getPhone()) != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists.");
//            }
//
//            // If all checks pass, save the user
//            userRepository.save(user);
//
//            return ResponseEntity.ok("User created successfully.");
//        } catch (DataIntegrityViolationException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user.");
//        }
//
//    }
//
//    @Transactional
//    public ResponseEntity<String> updateUser(User user) {
//        userRepository.findById(user.getUserId()).orElseThrow(() ->
//                new UserNotFoundException("NO USER PRESENT WITH ID = " + user.getUserId()));
//
//        try {
//            if (userRepository.findByUsername(user.getUsername()) != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
//            }
//            if (userRepository.findByEmail(user.getEmail()) != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
//            }
//            if (userRepository.findByPhone(user.getPhone()) != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists.");
//            }
//            userRepository.save(user);
//            return ResponseEntity.ok("User updated successfully.");
//
//        } catch (DataIntegrityViolationException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user.!!");
//            // Handle the unique constraint violation, e.g., return an error message to the user
//
//        }
//
//    }
//
//    @Transactional
//    public ResponseEntity<String> deleteById(Long id) {
//        userRepository.findById(id).orElseThrow(() ->
//                new UserNotFoundException("NO USER PRESENT WITH ID = " + id));
//        try {
//            userRepository.deleteById(id);
//            return ResponseEntity.ok("User deleted successfully.");
//
//        } catch (DataIntegrityViolationException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user.!!");
//            // Handle the unique constraint violation, e.g., return an error message to the user
//
//        }
//
//    }
//
//
//}
