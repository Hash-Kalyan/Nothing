package com.application.nothing.controller;

import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.dto.ProductDTO;
import com.application.nothing.dto.UpdateProductRequest;
import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.exception.ProductNotFoundException;
import com.application.nothing.model.Product;

import com.application.nothing.service.CategoryService;
import com.application.nothing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> productOptional = productService.getProductById(id);
        return productOptional.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        // Fetch the CategoryDTO based on the categoryId in productDTO
        Long categoryId = productDTO.getCategoryId();
        CategoryDTO categoryDTO = categoryService.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        // Call the service method with both ProductDTO and CategoryDTO
        ProductDTO createdProduct = productService.createProduct(productDTO, categoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    //    @PutMapping("/{id}")
//    public ResponseEntity<ResponseEntity<String>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//        product.setProductId(id);
//        // You might want to do some checks or modifications here before saving
//        return ResponseEntity.ok(productService.updateProduct(product));
//    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO
    ) {
        // Fetch the CategoryDTO based on the categoryId in productDTO
        Long categoryId = productDTO.getCategoryId();
        CategoryDTO categoryDTO = categoryService.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        // Call the service method with both ProductDTO and CategoryDTO
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO, categoryDTO);

        return ResponseEntity.ok(updatedProduct);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntity<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable String name) {
        List<ProductDTO> products = productService.findByProductName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(products);
    }


    // Exception handler for ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}


//package com.application.nothing.controller;
//
//import com.application.nothing.dto.CreateProductRequest;
//import com.application.nothing.model.Product;
//import com.application.nothing.dto.UpdateProductRequest;
//import com.application.nothing.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/products")
//public class ProductController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        return ResponseEntity.ok(productService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        Optional<Product> productOptional = productService.findById(id);
//        return productOptional.map(ResponseEntity::ok).orElseGet(() ->
//                ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
//        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
//    }
//
//    @PostMapping
//    public ResponseEntity<ResponseEntity<String>> createProduct(@RequestBody CreateProductRequest request) {
//        return ResponseEntity.ok(productService.createNewProduct(request.getProduct(), request.getCategoryId()));
//    }
//
////    @PutMapping("/{id}")
////    public ResponseEntity<ResponseEntity<String>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
////        product.setProductId(id);
////        // You might want to do some checks or modifications here before saving
////        return ResponseEntity.ok(productService.updateProduct(product));
////    }
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
//        return productService.updateProduct(id, request.getProduct(), request.getCategoryId());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseEntity<String>> deleteProduct(@PathVariable Long id) {
////        productService.deleteById(id);
////        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok(productService.deleteById(id));
//
//    }
//}
