package com.application.nothing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private Long shoppingCartId;
    private Long productId;
    private Integer quantity;
    private String message; // Add a message field for feedback

    // Getters and setters

}
