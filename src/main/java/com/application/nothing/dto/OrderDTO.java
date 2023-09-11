package com.application.nothing.dto;

import com.application.nothing.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;
    private User user;
    private Float totalPrice;
    private String status;
    private Date createdAt;
    private LocalDateTime shippedAt;

    // Getters and setters
    // ...

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", shippedAt=" + shippedAt +
                '}';
    }
}

