package com.application.nothing.service;

import com.application.nothing.repository.ShoppingCartRepository;
import com.application.nothing.model.ShoppingCart;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public void deleteById(Long id) {
        shoppingCartRepository.deleteById(id);
    }
}
