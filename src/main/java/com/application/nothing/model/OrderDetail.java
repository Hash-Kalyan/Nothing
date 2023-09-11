package com.application.nothing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "OrderDetails")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "orderDetailId")
@ToString
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Column(name = "sub_total", nullable = false)
    @NotNull(message = "Subtotal cannot be null")
    @Min(value = 0, message = "Subtotal must be a positive number")
    private Float subTotal;
}




//package com.application.nothing.model;
//
//import com.fasterxml.jackson.core.io.schubfach.FloatToDecimal;
//import jakarta.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "OrderDetails")
//public class OrderDetail {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_detail_id")
//    private Long orderDetailId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @Column(name = "quantity", nullable = false)
//    private Integer quantity;
//
//    @Column(name = "sub_total", nullable = false)
//    private Float subTotal;
//
//    // Getters, Setters, Constructors, equals, hashCode, and toString methods
//    // Getters and setters
//    public Long getOrderDetailId() {
//        return orderDetailId;
//    }
//
//    public void setOrderDetailId(Long orderDetailId) {
//        this.orderDetailId = orderDetailId;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Float getSubTotal() {
//        return subTotal;
//    }
//
//    public void setSubTotal(Float subTotal) {
//        this.subTotal = subTotal;
//    }
//
//    // equals, hashCode, and toString
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        OrderDetail that = (OrderDetail) o;
//        return Objects.equals(orderDetailId, that.orderDetailId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(orderDetailId);
//    }
//
//    @Override
//    public String toString() {
//        return "OrderDetail{" +
//                "orderDetailId=" + orderDetailId +
//                ", order=" + order +
//                ", product=" + product +
//                ", quantity=" + quantity +
//                ", subTotal=" + subTotal +
//                '}';
//    }
//}
//
