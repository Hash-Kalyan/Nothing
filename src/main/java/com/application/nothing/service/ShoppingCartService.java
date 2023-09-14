package com.application.nothing.service;

import com.application.nothing.dto.*;
import com.application.nothing.exception.ShoppingCartAlreadyExistsException;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.model.CartItem;
import com.application.nothing.model.ShoppingCart;
import com.application.nothing.model.User;
import com.application.nothing.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    //private CartItemService cartItemService;

    private final CartItemService cartItemService;

    @Autowired
    public ShoppingCartService(@Lazy CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    public List<ShoppingCartDTO> findAll() {
        return shoppingCartRepository.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ShoppingCartDTO> getShoppingCart(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping Cart not found with ID: " + cartId));
        return Optional.ofNullable(convertEntityToDTO(shoppingCart));
    }

    public void checkIfCartExists(Long userId) {
        if (shoppingCartRepository.existsByuser_userId(userId)) {
            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
        }
    }

//    public ShoppingCartDTO createNewShoppingCart(Long userId, ShoppingCartDTO shoppingCartDTO) {
//        // Check if a shopping cart already exists for the given user ID
//        if (shoppingCartRepository.existsByuser_userId(userId)) {
//            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
//        }
//
//        ShoppingCart shoppingCart = convertDTOToEntity(shoppingCartDTO);
//        shoppingCart.setUser(userService.findById(userId));
//        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
//        return convertEntityToDTO(savedShoppingCart);
//    }
//
//    public void checkIfCartExists(Long userId) {
//        if (shoppingCartRepository.existsByuser_userId(userId)) {
//            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
//        }
//    }

    public ShoppingCartDTO createNewShoppingCart(Long userId, ShoppingCartDTO shoppingCartDTO) {
        // Check if a shopping cart already exists for the given user ID
        if (shoppingCartRepository.existsByuser_userId(userId)) {
            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
        }

        ShoppingCart shoppingCart = convertDTOToEntity(shoppingCartDTO);
        userService.findById(userId).ifPresent(userDTO -> {
            User user = userService.convertDTOToEntity(userDTO);
            shoppingCart.setUser(user);
        });
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return convertEntityToDTO(savedShoppingCart);
    }

    public ShoppingCartDTO addItemToCart(CreateCartItemRequest createCartItemRequest) {
        // Logic for adding item to cart
        // ...
        return null; // Replace with actual implementation
    }

    public ShoppingCartDTO removeItemFromCart(RemoveCartItemRequest removeCartItemRequest) {
        // Logic for removing item from cart
        // ...
        return null; // Replace with actual implementation
    }

    public ShoppingCartDTO updateShoppingCart(UpdateShoppingCartRequest updateShoppingCartRequest) {
        // Logic for updating shopping cart
        // ...
        return null; // Replace with actual implementation
    }

    public ShoppingCartDTO convertEntityToDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCartId(shoppingCart.getCartId());
        shoppingCartDTO.setUserId(shoppingCart.getUser().getUserId());
        shoppingCartDTO.setItems(cartItemService.convertCartItemsToDTOs(shoppingCart.getItems()));
        return shoppingCartDTO;
    }

//    public ShoppingCart convertDTOToEntity(ShoppingCartDTO shoppingCartDTO) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setCartId(shoppingCartDTO.getCartId());
//
//        // Set user entity from UserDTO
//        Optional<UserDTO> userDTOOptional = userService.findById(shoppingCartDTO.getUserId());
//        if (userDTOOptional.isPresent()) {
//            UserDTO userDTO = userDTOOptional.get();
//            User user = userService.convertDTOToEntity(userDTO);
//            shoppingCart.setUser(user);
//
//        } else {
//            throw new UserNotFoundException("User not found with ID: " + shoppingCartDTO.getUserId());
//        }
//        // Set cart items from CartItemDTO list
//        List<CartItem> cartItems = shoppingCartDTO.getItems().stream()
//                .map(cartItemService::convertDTOToCartItem)
//                .collect(Collectors.toList());
//        shoppingCart.setItems(cartItems);
//
//        return shoppingCart;
//    }

    public ShoppingCart convertDTOToEntity(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartId(shoppingCartDTO.getCartId());

        // Set user entity from UserDTO
        userService.findById(shoppingCartDTO.getUserId()).ifPresent(userDTO -> {
            User user = userService.convertDTOToEntity(userDTO); // Convert UserDTO to User before setting
            shoppingCart.setUser(user);
        });

        // Set cart items from CartItemDTO list
        List<CartItem> cartItems = shoppingCartDTO.getItems().stream()
                .map(cartItemService::convertDTOToCartItem)
                .collect(Collectors.toList());
        shoppingCart.setItems(cartItems);

        return shoppingCart;
    }

}



//package com.application.nothing.service;
//
//import com.application.nothing.dto.CartItemDTO;
//import com.application.nothing.dto.ShoppingCartDTO;
//import com.application.nothing.dto.UserDTO;
//import com.application.nothing.model.CartItem;
//import com.application.nothing.model.ShoppingCart;
//import com.application.nothing.model.User;
//import com.application.nothing.repository.ShoppingCartRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ShoppingCartService {
//
//    @Autowired
//    private ShoppingCartRepository shoppingCartRepository;
//
//    // Additional services needed for conversion methods
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    public List<ShoppingCart> findAll() {
//        return shoppingCartRepository.findAll();
//    }
//
//    public Optional<ShoppingCart> findById(Long id) {
//        return shoppingCartRepository.findById(id);
//    }
//
//    public ShoppingCart save(ShoppingCart shoppingCart) {
//        return shoppingCartRepository.save(shoppingCart);
//    }
//
//    public void deleteById(Long id) {
//        shoppingCartRepository.deleteById(id);
//    }
//
//    public boolean existsById(Long id) {
//        return shoppingCartRepository.existsById(id);
//    }
//
//    // Convert ShoppingCart entity to ShoppingCartDTO
//    public ShoppingCartDTO convertEntityToDTO(ShoppingCart shoppingCart) {
//        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
//        shoppingCartDTO.setCartId(shoppingCart.getCartId());
//        shoppingCartDTO.setUserId(shoppingCart.getUser().getUserId());
//        shoppingCartDTO.setItems(cartItemService.convertCartItemsToDTOs(shoppingCart.getItems()));
//        return shoppingCartDTO;
//    }
//
//    // Convert ShoppingCartDTO to ShoppingCart entity
//    public ShoppingCart convertDTOToEntity(ShoppingCartDTO shoppingCartDTO) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setCartId(shoppingCartDTO.getCartId());
//
//        // Set user entity from UserDTO
//        UserDTO userDTO = userService.findById(shoppingCartDTO.getUserId()).orElse(null);
//        if (userDTO != null) {
//            User user = userService.convertDTOToEntity(userDTO);
//            shoppingCart.setUser(user);
//        }
//
//        // Set cart items from CartItemDTO list
//        List<CartItem> cartItems = shoppingCartDTO.getItems().stream()
//                .map(cartItemService::convertDTOToCartItem)
//                .collect(Collectors.toList());
//        shoppingCart.setItems(cartItems);
//
//        return shoppingCart;
//    }
//}


