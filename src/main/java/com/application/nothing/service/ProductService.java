package com.application.nothing.service;

import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.dto.ProductDTO;
import com.application.nothing.exception.ProductAlreadyExistsException;
import com.application.nothing.exception.ProductNotFoundException;
import com.application.nothing.model.Category;
import com.application.nothing.model.Product;
import com.application.nothing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final CategoryService categoryService;

    @Autowired
    public ProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDto);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, CategoryDTO categoryDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ProductAlreadyExistsException("Product already exists with name: " + productDTO.getName());
        }

        Product product = convertToEntity(productDTO, categoryDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, CategoryDTO categoryDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        updateProductProperties(existingProduct, productDTO, categoryDTO);
        productDTO.setProductId(id);
        Product updatedProduct = productRepository.save(convertToEntity(productDTO, categoryDTO));
        return convertToDto(updatedProduct);
    }


    private void updateProductProperties(Product product, ProductDTO productDTO, CategoryDTO categoryDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        // Update other properties of the product

        if (categoryDTO != null) {
            Category category = convertCategoryDTOToCategory(categoryDTO);
            product.setCategory(category);
        }
    }

    private Category convertCategoryDTOToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        // Map other properties from CategoryDTO to Category
        return category;
    }

    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategory_categoryId(categoryId);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Product convertToEntity(ProductDTO productDTO, CategoryDTO categoryDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        if (categoryDTO != null) {
            Category category = new Category();
            category.setCategoryId(categoryDTO.getCategoryId());
            // Map other properties from CategoryDTO to Category
            product.setCategory(category);
        }

        return product;
    }


    private ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        return productDTO;
    }

    public List<ProductDTO> findByProductName(String name) {
        List<Product> products = productRepository.findByName(name);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCreatedDate(product.getCreatedAt());
        //    productDTO.setLastUpdatedDate(product.getLastUpdatedDate());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        return productDTO;

    }
}


//package com.application.nothing.service;
//
//import com.application.nothing.dto.ProductDTO;
//import com.application.nothing.exception.ProductAlreadyExistsException;
//import com.application.nothing.exception.ProductNotFoundException;
//import com.application.nothing.model.Category;
//import com.application.nothing.model.Product;
//import com.application.nothing.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    // Other service methods here...
//
//    public List<ProductDTO> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    public Optional<ProductDTO> getProductById(Long id) {
//        Optional<Product> product = productRepository.findById(id);
//        return product.map(this::convertToDto);
//    }
//
//    public ProductDTO createNewProduct(ProductDTO productDTO) {
//        if (productRepository.existsByName(productDTO.getName())) {
//            throw new ProductAlreadyExistsException("Product already exists with name: " + productDTO.getName());
//        }
//        Product product = convertToEntity(productDTO);
//        Product savedProduct = productRepository.save(product);
//        return convertToDto(savedProduct);
//    }
//
//    public ProductDTO updateProduct(ProductDTO productDTO) {
//        Product product = convertToEntity(productDTO);
//        Product updatedProduct = productRepository.save(product);
//        return convertToDto(updatedProduct);
//    }
//
//    public ResponseEntity<Void> deleteById(Long id) {
//        if (!productRepository.existsById(id)) {
//            throw new ProductNotFoundException("Product not found with ID: " + id);
//        }
//        productRepository.deleteById(id);
//    }
//
//    public List<ProductDTO> findByCategoryId(Long categoryId) {
//        List<Product> products = productRepository.findByCategory_categoryId(categoryId);
//        return products.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private Product convertToEntity(ProductDTO productDTO) {
//        Product product = new Product();
//        product.setProductId(productDTO.getProductId());
//        product.setName(productDTO.getName());
//        product.setDescription(productDTO.getDescription());
//        product.setPrice(productDTO.getPrice());
//        Category category = CategoryService.findById(productDTO.getCategoryId()).orElse(null);
//        product.setCategory(category);
//        return product;
//    }
//
//    private ProductDTO convertToDto(Product product) {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setProductId(product.getProductId());
//        productDTO.setName(product.getName());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setCategoryId(product.getCategory().getCategoryId());
//        return productDTO;
//    }
//
//}

