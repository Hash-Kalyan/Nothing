package com.application.nothing.controller;

import com.application.nothing.model.dto.CreateProductRequest;
import com.application.nothing.model.Product;
import com.application.nothing.model.User;
import com.application.nothing.model.dto.UpdateProductRequest;
import com.application.nothing.service.ProductService;
import com.application.nothing.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @PostMapping
    public ResponseEntity<ResponseEntity<String>> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createNewProduct(request.getProduct(), request.getCategoryId()));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseEntity<String>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        product.setProductId(id);
//        // You might want to do some checks or modifications here before saving
//        return ResponseEntity.ok(productService.updateProduct(product));
//    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(id, request.getProduct(), request.getCategoryId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntity<String>> deleteProduct(@PathVariable Long id) {
//        productService.deleteById(id);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok(productService.deleteById(id));

    }
}
