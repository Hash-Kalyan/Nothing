package com.application.nothing.service;

import com.application.nothing.dto.OrderDTO;
import com.application.nothing.exception.OrderAlreadyExistsException;
import com.application.nothing.exception.OrderNotFoundException;
import com.application.nothing.model.Order;
import com.application.nothing.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDTO> findAll() {
        logger.info("Fetching all orders");
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> findById(Long id) {
        logger.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public OrderDTO save(OrderDTO orderDTO) {
        Long orderId = orderDTO.getOrderId();
        if (orderId != null && orderRepository.existsById(orderId)) {
            logger.info("Updating order with ID: {}", orderId);
        } else if (orderId != null) {
            throw new OrderAlreadyExistsException("An order with this ID already exists");
        } else {
            logger.info("Creating new order");
        }

        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting order with ID: {}", id);
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("No order found with this ID");
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
}




//package com.application.nothing.service;
//
//import com.application.nothing.exception.OrderNotFoundException;
//import com.application.nothing.model.Order;
//import com.application.nothing.dto.OrderDTO;
//import com.application.nothing.repository.OrderRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderService {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public List<OrderDTO> findAll() {
//        List<OrderDTO> orders = orderRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//        if (orders.isEmpty()) {
//            throw new OrderNotFoundException("No orders found");
//        }
//        return orders;
//    }
//
//    public Optional<OrderDTO> findById(Long id) {
//        return orderRepository.findById(id)
//                .map(this::convertToDTO);
//    }
//
//    public List<OrderDTO> findOrdersByUserId(Long userId) {
//        return orderRepository.findByUserId(userId).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public OrderDTO save(OrderDTO orderDTO) {
//        Order order = convertToEntity(orderDTO);
//        Order savedOrder = orderRepository.save(order);
//        return convertToDTO(savedOrder);
//    }
//
//    public void deleteById(Long id) {
//        if (!orderRepository.existsById(id)) {
//            throw new OrderNotFoundException("Order with ID " + id + " not found");
//        }
//        orderRepository.deleteById(id);
//    }
//
//    public boolean existsById(Long id) {
//        return orderRepository.existsById(id);
//    }
//
//    public Optional<Order> findEntityById(Long id) {
//        return orderRepository.findById(id);
//    }
//
//    private OrderDTO convertToDTO(Order order) {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setOrderId(order.getOrderId());
//        orderDTO.setUser(order.getUser());
//        orderDTO.setTotalPrice(order.getTotalPrice());
//        orderDTO.setStatus(order.getStatus());
//        orderDTO.setCreatedAt(order.getCreatedAt());
//        orderDTO.setShippedAt(order.getShippedAt());
//        return orderDTO;
//    }
//
//    private Order convertToEntity(OrderDTO orderDTO) {
//        Order order = new Order();
//        order.setOrderId(orderDTO.getOrderId());
//        order.setUser(orderDTO.getUser());
//        order.setTotalPrice(orderDTO.getTotalPrice());
//        order.setStatus(orderDTO.getStatus());
//        order.setCreatedAt(orderDTO.getCreatedAt());
//        order.setShippedAt(orderDTO.getShippedAt());
//        return order;
//    }
//}
