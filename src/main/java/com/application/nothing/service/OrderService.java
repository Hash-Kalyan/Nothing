package com.application.nothing.service;

import com.application.nothing.exception.OrderNotFoundException;
import com.application.nothing.model.Order;
import com.application.nothing.dto.OrderDTO;
import com.application.nothing.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> findAll() {
        List<OrderDTO> orders = orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return orders;
    }

    public Optional<OrderDTO> findById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<OrderDTO> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO save(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public void deleteById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    public Optional<Order> findEntityById(Long id) {
        return orderRepository.findById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUser(order.getUser());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setShippedAt(order.getShippedAt());
        return orderDTO;
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setUser(orderDTO.getUser());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setShippedAt(orderDTO.getShippedAt());
        return order;
    }
}




//package com.application.nothing.service;
//
//import com.application.nothing.repository.OrderRepository;
//import com.application.nothing.model.Order;
//import com.application.nothing.exception.OrderNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class OrderService {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
//
//    @Autowired
//    private OrderRepository orderRepository;
//    public List<Order> findAll() {
//        return orderRepository.findAll();
//    }
//
//    public Optional<Order> findById(Long id) {
//        return orderRepository.findById(id);
//    }
//
//    public Order save(Order order) {
//        return orderRepository.save(order);
//    }
//
//    public void deleteById(Long id) {
//        orderRepository.deleteById(id);
//    }
//
//
//}
