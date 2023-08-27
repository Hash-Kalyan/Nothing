package com.application.nothing.service;

import com.application.nothing.repository.CartItemRepository;
import com.application.nothing.model.CartItem;
import com.application.nothing.exception.CartItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }

    public boolean existsById(Long id) {return cartItemRepository.existsById(id); }
}
