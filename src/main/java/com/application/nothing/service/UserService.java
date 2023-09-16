package com.application.nothing.service;

import com.application.nothing.dto.UserDTO;
import com.application.nothing.exception.UserAlreadyExistsException;
import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.model.User;
import com.application.nothing.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    public UserDTO save(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            logger.error("User creation failed. A user with email {} already exists", userDTO.getEmail());
            throw new UserAlreadyExistsException("A user with this email already exists");
        }

        logger.info("Creating new user with details: {}", userDTO);
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getUserId());
        return convertToDto(savedUser);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("No user found with this ID");
        }
        userRepository.deleteById(id);
    }

    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        // Update the lastLogin attribute with the current date and time
        user.setLastLogin(new Date());
        userRepository.save(user);

        logger.info("User with email {} logged in at {}", email, user.getLastLogin());
        return convertToDto(user);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
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
