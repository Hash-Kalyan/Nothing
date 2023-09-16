package com.application.nothing.service;

import com.application.nothing.dto.CartItemDTO;
import com.application.nothing.exception.CartItemAlreadyExistsException;
import com.application.nothing.exception.CartItemNotFoundException;
import com.application.nothing.model.CartItem;
import com.application.nothing.repository.CartItemRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CartItemDTO> findAll() {
        return cartItemRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<CartItemDTO> findById(Long id) {
        return cartItemRepository.findById(id)
                .map(this::convertToDto);
    }

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        if (cartItemRepository.existsById(cartItemDTO.getCartItemId())) {
            logger.error("Cart item already exists with ID: {}", cartItemDTO.getCartItemId());
            throw new CartItemAlreadyExistsException("Cart item already exists. Use update to modify quantity.");
        }
        CartItem cartItem = convertToEntity(cartItemDTO);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return convertToDto(savedCartItem);
    }

    public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cart item not found with ID: {}", id);
                    return new CartItemNotFoundException("Cart item not found");
                });

        existingCartItem.setQuantity(cartItemDTO.getQuantity());
        // Add other fields here that need to be updated

        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
        return convertToDto(updatedCartItem);
    }

    public void deleteCartItem(Long id) {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cart item not found with ID: {}", id);
                    return new CartItemNotFoundException("Cart item not found");
                });
        cartItemRepository.delete(existingCartItem);
    }

    public boolean existsById(Long id) {
        return cartItemRepository.existsById(id);
    }

    private CartItem convertToEntity(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    private CartItemDTO convertToDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }
}


//package com.application.nothing.service;
//
//import com.application.nothing.dto.CartItemDTO;
//import com.application.nothing.dto.ShoppingCartDTO;
//import com.application.nothing.dto.ProductDTO;
//import com.application.nothing.dto.UserDTO;
//import com.application.nothing.exception.CartItemNotFoundException;
//import com.application.nothing.exception.ProductNotFoundException;
//import com.application.nothing.exception.ShoppingCartNotFoundException;
//import com.application.nothing.exception.UserNotFoundException;
//import com.application.nothing.model.CartItem;
//import com.application.nothing.model.ShoppingCart;
//import com.application.nothing.model.Product;
//import com.application.nothing.model.User;
//import com.application.nothing.repository.ProductRepository;
//import com.application.nothing.service.ProductService;
//import com.application.nothing.service.UserService;
//import com.application.nothing.model.Product;
//import com.application.nothing.repository.CartItemRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class CartItemService {
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    //private ShoppingCartService shoppingCartService;
//
//    private final ShoppingCartService shoppingCartService;
//
//    @Autowired
//    public CartItemService(@Lazy ShoppingCartService shoppingCartService) {
//        this.shoppingCartService = shoppingCartService;
//    }
//
//    //private final UserService userService;
//    //@Autowired
//    //public CartItemService(UserService userService) {this.userService = userService;}
//
//
//    public List<CartItemDTO> findAll() {
//        log.debug("Fetching all cart items");
//        List<CartItem> cartItems = cartItemRepository.findAll();
//        return cartItems.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public Optional<CartItemDTO> findById(Long id) {
//        log.debug("Fetching cart item by ID: {}", id);
//        return cartItemRepository.findById(id).map(this::convertToDTO);
//    }
//
//    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
//        Optional<CartItem> existingCartItem = cartItemRepository.findByShoppingCart_cartIdAndProduct_ProductId(
//                cartItemDTO.getShoppingCartId(), cartItemDTO.getProductId());
//
//        CartItem cartItem;
//        if (existingCartItem.isPresent()) {
//            cartItem = existingCartItem.get();
//            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
//            cartItem = cartItemRepository.save(cartItem);
//        } else {
//            cartItem = convertDTOToCartItem(cartItemDTO);
//            cartItem = cartItemRepository.save(cartItem);
//        }
//        return convertToDTO(cartItem);
//    }
//
//    public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
//        if (!cartItemRepository.existsById(id)) {
//            throw new CartItemNotFoundException("Cart item with ID " + id + " not found");
//        }
//        CartItem cartItem = convertDTOToCartItem(cartItemDTO);
//        cartItem.setCartItemId(id);
//        cartItem = cartItemRepository.save(cartItem);
//        return convertToDTO(cartItem);
//    }
//
//    public void deleteCartItem(Long id) {
//        if (!cartItemRepository.existsById(id)) {
//            throw new CartItemNotFoundException("Cart item with ID " + id + " not found");
//        }
//        cartItemRepository.deleteById(id);
//    }
//
//    private CartItemDTO convertToDTO(CartItem cartItem) {
//        return new CartItemDTO(
//                cartItem.getCartItemId(),
//                cartItem.getShoppingCart().getCartId(),
//                cartItem.getProduct().getProductId(),
//                cartItem.getPrice(),
//                cartItem.getQuantity(),
//                "" // or any other field that you have in CartItemDTO
//        );
//    }
//
//    public List<CartItemDTO> convertCartItemsToDTOs(List<CartItem> cartItems) {
//        return cartItems.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
////    private CartItem convertDTOToCartItem(CartItemDTO cartItemDTO) {
////        CartItem cartItem = new CartItem();
////        cartItem.setCartItemId(cartItemDTO.getCartItemId());
////        cartItem.setProduct(convertToProductEntity(cartItemDTO).orElseThrow(
////                () -> new ProductNotFoundException("Product with ID " + cartItemDTO.getProductId() + " not found")));
////        cartItem.setShoppingCart(convertToShoppingCartEntity(cartItemDTO).orElseThrow(
////                () -> new ShoppingCartNotFoundException("Shopping cart with ID " + cartItemDTO.getShoppingCartId() + " not found")));
////        cartItem.setQuantity(cartItemDTO.getQuantity());
////        return cartItem;
////    }
//
//    public CartItem convertDTOToCartItem(CartItemDTO cartItemDTO) {
//        // Implement logic to fetch product and shopping cart details using other services
//        CartItem cartItem = new CartItem();
//        cartItem.setCartItemId(cartItemDTO.getCartItemId());
//
//        ProductDTO productDTO = productService.getProductById(cartItemDTO.getProductId()).orElseThrow(
//                () -> new ProductNotFoundException("Product with ID " + cartItemDTO.getProductId() + " not found"));
//        cartItem.setProduct(convertToProductEntity(productDTO));
//
//        ShoppingCartDTO shoppingCartDTO = shoppingCartService.findById(cartItemDTO.getShoppingCartId()).orElseThrow(
//                () -> new ShoppingCartNotFoundException("Shopping cart with ID " + cartItemDTO.getShoppingCartId() + " not found"));
//        cartItem.setShoppingCart(convertToShoppingCartEntity(shoppingCartDTO));
//
//        cartItem.setQuantity(cartItemDTO.getQuantity());
//        // Set other fields as necessary
//        // ...
//        return cartItem;
//    }
//
//
//    private Product convertToProductEntity(ProductDTO productDTO) {
//        if (productDTO == null) {
//            return null;
//        }
//        return productRepository.findById(productDTO.getProductId())
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productDTO.getProductId()));
//    }
//    private ShoppingCart convertToShoppingCartEntity(ShoppingCartDTO shoppingCartDTO) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setCartId(shoppingCartDTO.getCartId());
//        UserDTO userDTO = userService.findById(shoppingCartDTO.getUserId()).orElse(null);
//        if (userDTO == null) {
//            throw new UserNotFoundException("User not found with ID: " + shoppingCartDTO.getUserId());
//        }
//        User user = convertUserDTOToUser(userDTO);
//        shoppingCart.setUser(user);
//        shoppingCart.setItems(shoppingCartDTO.getItems().stream().map(this::convertDTOToCartItem).collect(Collectors.toList()));
//        return shoppingCart;
//    }
//
//
//
//    private User convertUserDTOToUser(UserDTO userDTO) {
//        if (userDTO == null) {
//            return null;
//        }
//        User user = new User();
//        user.setUserId(userDTO.getUserId());
//        // Set other fields of the User entity from UserDTO
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword());
//        user.setEmail(userDTO.getEmail());
//        user.setPhone(userDTO.getPhone());
//        user.setAddress(user.getAddress());
//        user.setCreatedAt(userDTO.getCreatedAt());
//        return user;
//    }
//
//    //private Product convertToProductEntity(ProductDTO productDTO) {
//    //    // Here, you will implement the logic to convert ProductDTO to Product entity
//    //    // This is a dummy implementation, replace it with actual logic
//    //    Product product = new Product();
//    //    product.setProductId(productDTO.getProductId());
//    //    product.setName(productDTO.getName());
//    //    // ... (set other fields)
//    //    return product;
//    //}
//
//    //private ShoppingCart convertToShoppingCartEntity(ShoppingCartDTO shoppingCartDTO) {
//    //    // Here, you will implement the logic to convert ShoppingCartDTO to ShoppingCart entity
//    //    // This is a dummy implementation, replace it with actual logic
//    //    ShoppingCart shoppingCart = new ShoppingCart();
//    //    shoppingCart.setCartId(shoppingCartDTO.getCartId());
//    //    // ... (set other fields)
//    //    return shoppingCart;
//    //}
//
//    public boolean existsById(Long id) {
//        return cartItemRepository.existsById(id);
//    }
//}