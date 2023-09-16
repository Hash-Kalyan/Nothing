package com.application.nothing.service;

import com.application.nothing.dto.*;
import com.application.nothing.exception.ShoppingCartAlreadyExistsException;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import com.application.nothing.exception.CartItemNotFoundException;
import com.application.nothing.model.CartItem;
import com.application.nothing.model.ShoppingCart;
import com.application.nothing.model.User;
import com.application.nothing.repository.ShoppingCartRepository;
import com.application.nothing.repository.CartItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    //private CartItemService cartItemService;

    private final CartItemService cartItemService;

    @Autowired
    public ShoppingCartService(@Lazy CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

//  this code has been moved to project config -> BeanConfig
//    @Configuration
//    public static class AppConfig {
//
//        @Bean
//        public ModelMapper modelMapper() {
//            return new ModelMapper();
//        }
//    }


    public ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = convertToEntity(shoppingCartDTO);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return convertToDto(savedShoppingCart);
    }

    public Optional<ShoppingCartDTO> findById(Long cartId) {
        return shoppingCartRepository.findById(cartId).map(this::convertToDto);
    }

    public ShoppingCartDTO updateShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = convertToEntity(shoppingCartDTO);
        ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return convertToDto(updatedShoppingCart);
    }

    public ShoppingCartDTO addItemToCart(Long cartId, CartItemDTO cartItemDTO) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);
        shoppingCart.addCartItem(cartItem);

        ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return convertToDto(updatedShoppingCart);
    }

    public void removeItemFromCart(Long cartId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

        shoppingCart.removeCartItem(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCartDTO checkoutCart(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        shoppingCart.checkout();
        ShoppingCart checkedOutCart = shoppingCartRepository.save(shoppingCart);
        return convertToDto(checkedOutCart);
    }

    private ShoppingCart convertToEntity(ShoppingCartDTO shoppingCartDTO) {
        return modelMapper.map(shoppingCartDTO, ShoppingCart.class);
    }

    private ShoppingCartDTO convertToDto(ShoppingCart shoppingCart) {
        return modelMapper.map(shoppingCart, ShoppingCartDTO.class);
    }
}



//package com.application.nothing.service;
//
//import com.application.nothing.dto.*;
//import com.application.nothing.exception.ShoppingCartAlreadyExistsException;
//import com.application.nothing.exception.ShoppingCartNotFoundException;
//import com.application.nothing.exception.UserNotFoundException;
//import com.application.nothing.model.CartItem;
//import com.application.nothing.model.ShoppingCart;
//import com.application.nothing.model.User;
//import com.application.nothing.repository.ShoppingCartRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
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
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    //private CartItemService cartItemService;
//
//    private final CartItemService cartItemService;
//
//    @Autowired
//    public ShoppingCartService(@Lazy CartItemService cartItemService) {
//        this.cartItemService = cartItemService;
//    }
//
//    public List<ShoppingCartDTO> findAll() {
//        return shoppingCartRepository.findAll().stream()
//                .map(this::convertEntityToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public Optional<ShoppingCartDTO> getShoppingCart(Long cartId) {
//        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
//                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping Cart not found with ID: " + cartId));
//        return Optional.ofNullable(convertEntityToDTO(shoppingCart));
//    }
//
//    public void checkIfCartExists(Long userId) {
//        if (shoppingCartRepository.existsByuser_userId(userId)) {
//            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
//        }
//    }
//
////    public ShoppingCartDTO createNewShoppingCart(Long userId, ShoppingCartDTO shoppingCartDTO) {
////        // Check if a shopping cart already exists for the given user ID
////        if (shoppingCartRepository.existsByuser_userId(userId)) {
////            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
////        }
////
////        ShoppingCart shoppingCart = convertDTOToEntity(shoppingCartDTO);
////        shoppingCart.setUser(userService.findById(userId));
////        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
////        return convertEntityToDTO(savedShoppingCart);
////    }
////
////    public void checkIfCartExists(Long userId) {
////        if (shoppingCartRepository.existsByuser_userId(userId)) {
////            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
////        }
////    }
//
//    public ShoppingCartDTO createNewShoppingCart(Long userId, ShoppingCartDTO shoppingCartDTO) {
//        // Check if a shopping cart already exists for the given user ID
//        if (shoppingCartRepository.existsByuser_userId(userId)) {
//            throw new ShoppingCartAlreadyExistsException("Shopping Cart already exists for user ID: " + userId);
//        }
//
//        ShoppingCart shoppingCart = convertDTOToEntity(shoppingCartDTO);
//        userService.findById(userId).ifPresent(userDTO -> {
//            User user = userService.convertDTOToEntity(userDTO);
//            shoppingCart.setUser(user);
//        });
//        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
//        return convertEntityToDTO(savedShoppingCart);
//    }
//
//    public ShoppingCartDTO addItemToCart(CreateCartItemRequest createCartItemRequest) {
//        // Logic for adding item to cart
//        // ...
//        return null; // Replace with actual implementation
//    }
//
//    public ShoppingCartDTO removeItemFromCart(RemoveCartItemRequest removeCartItemRequest) {
//        // Logic for removing item from cart
//        // ...
//        return null; // Replace with actual implementation
//    }
//
//    public ShoppingCartDTO updateShoppingCart(UpdateShoppingCartRequest updateShoppingCartRequest) {
//        // Logic for updating shopping cart
//        // ...
//        return null; // Replace with actual implementation
//    }
//
//    public ShoppingCartDTO convertEntityToDTO(ShoppingCart shoppingCart) {
//        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
//        shoppingCartDTO.setCartId(shoppingCart.getCartId());
//        shoppingCartDTO.setUserId(shoppingCart.getUser().getUserId());
//        shoppingCartDTO.setItems(cartItemService.convertCartItemsToDTOs(shoppingCart.getItems()));
//        return shoppingCartDTO;
//    }
//
////    public ShoppingCart convertDTOToEntity(ShoppingCartDTO shoppingCartDTO) {
////        ShoppingCart shoppingCart = new ShoppingCart();
////        shoppingCart.setCartId(shoppingCartDTO.getCartId());
////
////        // Set user entity from UserDTO
////        Optional<UserDTO> userDTOOptional = userService.findById(shoppingCartDTO.getUserId());
////        if (userDTOOptional.isPresent()) {
////            UserDTO userDTO = userDTOOptional.get();
////            User user = userService.convertDTOToEntity(userDTO);
////            shoppingCart.setUser(user);
////
////        } else {
////            throw new UserNotFoundException("User not found with ID: " + shoppingCartDTO.getUserId());
////        }
////        // Set cart items from CartItemDTO list
////        List<CartItem> cartItems = shoppingCartDTO.getItems().stream()
////                .map(cartItemService::convertDTOToCartItem)
////                .collect(Collectors.toList());
////        shoppingCart.setItems(cartItems);
////
////        return shoppingCart;
////    }
//
//    public ShoppingCart convertDTOToEntity(ShoppingCartDTO shoppingCartDTO) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setCartId(shoppingCartDTO.getCartId());
//
//        // Set user entity from UserDTO
//        userService.findById(shoppingCartDTO.getUserId()).ifPresent(userDTO -> {
//            User user = userService.convertDTOToEntity(userDTO); // Convert UserDTO to User before setting
//            shoppingCart.setUser(user);
//        });
//
//        // Set cart items from CartItemDTO list
//        List<CartItem> cartItems = shoppingCartDTO.getItems().stream()
//                .map(cartItemService::convertDTOToCartItem)
//                .collect(Collectors.toList());
//        shoppingCart.setItems(cartItems);
//
//        return shoppingCart;
//    }
//
//}
