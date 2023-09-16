package com.application.nothing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;  // Assuming that we'll map the user ID from the User entity

    @NotNull(message = "Total price cannot be null")
    private BigDecimal totalPrice;

    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;  // Using the same enum type as in the Order entity

    @NotNull(message = "Order Created date cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Order Shipped date cannot be null")
    private LocalDateTime shippedAt;


    // Additional methods if any

}
enum OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELLED;
}


