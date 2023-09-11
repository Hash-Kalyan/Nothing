package com.application.nothing.service;

import com.application.nothing.dto.OrderDetailDTO;
import com.application.nothing.exception.OrderDetailNotFoundException;
import com.application.nothing.exception.OrderNotFoundException;
import com.application.nothing.exception.ProductNotFoundException;
import com.application.nothing.model.Order;
import com.application.nothing.model.OrderDetail;
import com.application.nothing.model.Product;
import com.application.nothing.repository.OrderDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailService.class);

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    public Optional<OrderDetailDTO> findByOrderIdAndProductId(Long orderId, Long productId) {
        return orderDetailRepository.findByOrder_orderIdAndProduct_productId(orderId, productId)
                .map(this::convertToDTO);
    }

    public boolean existsById(Long id) {
        return orderDetailRepository.existsById(id);
    }

    public List<OrderDetailDTO> findAll() {
        return orderDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDetailDTO> findById(Long id) {
        return orderDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return convertToDTO(savedOrderDetail);
    }

    public void deleteById(Long id) {
        if (!orderDetailRepository.existsById(id)) {
            throw new OrderDetailNotFoundException("OrderDetail with ID " + id + " not found");
        }
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailDTO.setOrderId(orderDetail.getOrder().getOrderId());
        orderDetailDTO.setProductId(orderDetail.getProduct().getProductId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setSubTotal(orderDetail.getSubTotal());
        return orderDetailDTO;
    }


//    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
//        orderDetail.setOrder(orderService.findById(orderDetailDTO.getOrderId()).orElseThrow(
//                () -> new OrderNotFoundException("Order with ID " + orderDetailDTO.getOrderId() + " not found"))
//        );
//        orderDetail.setProduct(productService.findById(orderDetailDTO.getProductId()).orElseThrow(
//                () -> new ProductNotFoundException("Product with ID " + orderDetailDTO.getProductId() + " not found"))
//        );
//        orderDetail.setQuantity(orderDetailDTO.getQuantity());
//        orderDetail.setSubTotal(orderDetailDTO.getSubTotal());
//        return orderDetail;
//    }

//    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
//
//        // Here we get the Order and Product entities from the database using the service classes
//        orderDetail.setOrder(orderService.findEntityById(orderDetailDTO.getOrderId())
//                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderDetailDTO.getOrderId() + " not found")));
//        orderDetail.setProduct(productService.findEntityById(orderDetailDTO.getProductId())
//                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + orderDetailDTO.getProductId() + " not found")));
//
//        orderDetail.setQuantity(orderDetailDTO.getQuantity());
//        orderDetail.setSubTotal(orderDetailDTO.getSubTotal());
//        return orderDetail;
//    }


//    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
//
//        orderService.findById(orderDetailDTO.getOrderId()).ifPresent(orderDTO -> {
//            Order order = new Order();
//            order.setOrderId(orderDTO.getOrderId());
//            orderDetail.setOrder(order);
//        });
//
//        productService.findById(orderDetailDTO.getProductId()).ifPresent(productDTO -> {
//            Product product = new Product();
//            product.setProductId(productDTO.getProductId());
//            orderDetail.setProduct(product);
//        });
//
//        orderDetail.setQuantity(orderDetailDTO.getQuantity());
//        orderDetail.setSubTotal(orderDetailDTO.getSubTotal());
//        return orderDetail;
//    }

    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());

        orderDetail.setOrder(orderService.findEntityById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderDetailDTO.getOrderId() + " not found")));

        orderDetail.setProduct(productService.findEntityById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + orderDetailDTO.getProductId() + " not found")));

        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setSubTotal(orderDetailDTO.getSubTotal());
        return orderDetail;
    }


}





//package com.application.nothing.service;
//
//import com.application.nothing.repository.OrderDetailRepository;
//import com.application.nothing.model.OrderDetail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class OrderDetailService {
//
//    @Autowired
//    private OrderDetailRepository orderDetailRepository;
//
//    public List<OrderDetail> findAll() {
//        return orderDetailRepository.findAll();
//    }
//
//    public Optional<OrderDetail> findById(Long id) {
//        return orderDetailRepository.findById(id);
//    }
//
//    public OrderDetail save(OrderDetail orderDetail) {
//        return orderDetailRepository.save(orderDetail);
//    }
//
//    public void deleteById(Long id) {
//        orderDetailRepository.deleteById(id);
//    }
//}
