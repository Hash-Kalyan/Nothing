package com.application.nothing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private Long cartId;
    private Long userId;
    private List<CartItemDTO> items;

    // Removed cartTotal as it's not present in the entity class

    // Additional fields and methods from UpdateShoppingCartRequest can go here, if any
    // Additional fields can be added here based on the requirements

}

