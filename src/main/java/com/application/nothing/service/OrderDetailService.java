package com.application.nothing.service;

import com.application.nothing.dto.OrderDetailDTO;
import com.application.nothing.exception.OrderDetailNotFoundException;
import com.application.nothing.model.OrderDetail;
import com.application.nothing.repository.OrderDetailRepository;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public List<OrderDetailDTO> findAll() {
        return orderDetailRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<OrderDetailDTO> findById(Long id) {
        return orderDetailRepository.findById(id)
                .map(this::convertToDto);
    }

    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return convertToDto(savedOrderDetail);
    }

    public void deleteById(Long id) {
        if (!orderDetailRepository.existsById(id)) {
            throw new OrderDetailNotFoundException("No order detail found with this ID");
        }
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailDTO convertToDto(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }
}



//package com.application.nothing.service;
//
//import com.application.nothing.dto.CategoryDTO;
//import com.application.nothing.dto.OrderDetailDTO;
//import com.application.nothing.dto.ProductDTO;
//import com.application.nothing.exception.CategoryNotFoundException;
//import com.application.nothing.exception.OrderDetailNotFoundException;
//import com.application.nothing.exception.OrderNotFoundException;
//import com.application.nothing.exception.ProductNotFoundException;
//import com.application.nothing.model.Category;
//import com.application.nothing.model.Order;
//import com.application.nothing.model.OrderDetail;
//import com.application.nothing.model.Product;
//import com.application.nothing.repository.OrderDetailRepository;
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
//public class OrderDetailService {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderDetailService.class);
//
//    @Autowired
//    private OrderDetailRepository orderDetailRepository;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    public Optional<OrderDetailDTO> findByOrderIdAndProductId(Long orderId, Long productId) {
//        return orderDetailRepository.findByOrder_orderIdAndProduct_productId(orderId, productId)
//                .map(this::convertToDTO);
//    }
//
//    public boolean existsById(Long id) {
//        return orderDetailRepository.existsById(id);
//    }
//
//    public List<OrderDetailDTO> findAll() {
//        return orderDetailRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public Optional<OrderDetailDTO> findById(Long id) {
//        return orderDetailRepository.findById(id)
//                .map(this::convertToDTO);
//    }
//
//    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
//        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
//        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
//        return convertToDTO(savedOrderDetail);
//    }
//
//    public void deleteById(Long id) {
//        if (!orderDetailRepository.existsById(id)) {
//            throw new OrderDetailNotFoundException("OrderDetail with ID " + id + " not found");
//        }
//        orderDetailRepository.deleteById(id);
//    }
//
//    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
//        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
//        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
//        orderDetailDTO.setOrderId(orderDetail.getOrder().getOrderId());
//        orderDetailDTO.setProductId(orderDetail.getProduct().getProductId());
//        orderDetailDTO.setQuantity(orderDetail.getQuantity());
//        orderDetailDTO.setSubTotal(orderDetail.getSubTotal());
//        return orderDetailDTO;
//    }
//}