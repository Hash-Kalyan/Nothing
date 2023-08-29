package com.application.nothing.service;

import com.application.nothing.exception.UserAlreadyExistsException;
import com.application.nothing.model.Product;
import com.application.nothing.repository.UserRepository;
import com.application.nothing.model.User;
import com.application.nothing.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("NO USER PRESENT WITH ID = " + id)));
    }

    @Transactional
    public String createNewUser(User user) {
        try {
            userRepository.save(user);
            return "User added successfully";
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException("Customer already exixts!!");
            // Handle the unique constraint violation, e.g., return an error message to the user

        }

    }

    @Transactional
    public String updateUser(User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() ->
                new UserNotFoundException("NO USER PRESENT WITH ID = " + user.getUserId()));

        try {
            userRepository.save(user);
            return "User updated successfully";
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException("Customer already exixts!!");
            // Handle the unique constraint violation, e.g., return an error message to the user

        }

    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // Any other additional methods or business logic can go here.
}
