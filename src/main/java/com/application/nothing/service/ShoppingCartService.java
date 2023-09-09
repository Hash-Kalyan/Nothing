package com.application.nothing.service;

import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.model.User;
import com.application.nothing.repository.ShoppingCartRepository;
import com.application.nothing.model.ShoppingCart;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Transactional(readOnly = true)
    public List<ShoppingCart> findAll() {

        return shoppingCartRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ShoppingCart> findById(Long id) {

        return Optional.ofNullable(shoppingCartRepository.findById(id).orElseThrow(() ->
                new ShoppingCartNotFoundException("NO ShoppingCart PRESENT WITH ID = " + id)));
    }

    @Transactional
    public ResponseEntity<String> createNewShoppingCart(ShoppingCart shoppingCart) {
        try {
            shoppingCartRepository.save(shoppingCart);
            return ResponseEntity.ok("ShoppingCart created successfully.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Shopping Cart.");
        }
    }

    @Transactional
    public ResponseEntity<String> updateShoppingCart(ShoppingCart shoppingCart) {
        try {
            shoppingCartRepository.save(shoppingCart);
            return ResponseEntity.ok("ShoppingCart updated successfully.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Shopping Cart.");
        }
    }

    @Transactional
    public ResponseEntity<String> deleteById(Long id) {
        shoppingCartRepository.findById(id).orElseThrow(() ->
                new ShoppingCartNotFoundException("NO ShoppingCart PRESENT WITH ID = " + id));;
        try {
            shoppingCartRepository.deleteById(id);
            return ResponseEntity.ok("ShoppingCart deleted successfully.");

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting ShoppingCart.!!");
            // Handle the unique constraint violation, e.g., return an error message to the user

        }

    }
}
