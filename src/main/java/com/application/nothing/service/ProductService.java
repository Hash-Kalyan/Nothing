package com.application.nothing.service;

import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.dto.ProductDTO;
import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.exception.ProductAlreadyExistsException;
import com.application.nothing.exception.ProductNotFoundException;
import com.application.nothing.model.Category;
import com.application.nothing.model.Product;
import com.application.nothing.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    private final CategoryService categoryService;

    @Autowired
    public ProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Fetches all products.
     *
     * @return a list of ProductDTO objects.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetches a product by its ID.
     *
     * @param id the ID of the product to be fetched.
     * @return an optional containing the ProductDTO object if found.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDto);
    }

    /**
     * Creates a new product.
     *
     * @param productDTO the product details.
//     * @param categoryDTO the category details.
     * @return the created ProductDTO object.
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Creating new product: {}", productDTO);

        if (productRepository.existsByName(productDTO.getName())) {
            throw new ProductAlreadyExistsException("A product with the name '" + productDTO.getName() + "' already exists");
        }
        // Fetch the Category entity based on the categoryId in productDTO
        Long categoryId = productDTO.getCategoryId();
        Category category = categoryService.findCategoryById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("Category with ID " + categoryId + " not found");
        }


        // Convert DTO to Entity, save it, and then convert the saved entity back to DTO
        Product product = convertToEntity(productDTO, category);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    private Product convertToEntity(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);  // Here we set the Category entity directly
        // ... (set other fields as necessary)

        return product;
    }

    // ... (other methods)
    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to be updated.
     * @param productDTO the updated product details.
//     * @param categoryDTO the category details.
     * @return the updated ProductDTO object.
     */
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        logger.info("Updating product with ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ProductNotFoundException("Product not found");
                });

        // Fetch the Category entity based on the categoryId in productDTO
        Long categoryId = productDTO.getCategoryId();
        Category category = categoryService.findCategoryById(categoryId);

        // Update the existing product with the new details
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }



    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted.
     */
    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting product with ID: {}", id);

        if (!productRepository.existsById(id)) {
            logger.error("Product not found with ID: {}", id);
            throw new ProductNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }

// ... (other methods, including convertToDto and convertToEntity methods)
    /**
     * Fetches products by category ID.
     *
     * @param categoryId the ID of the category whose products are to be fetched.
     * @return a list of ProductDTO objects belonging to the specified category.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        logger.info("Fetching products by category ID: {}", categoryId);

        // Fetch products belonging to the specified category
        List<Product> products = productRepository.findAllByCategoryId(categoryId);

        // Convert the list of Product entities to a list of ProductDTO objects
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a ProductDTO object to a Product entity.
     *
     * @param productDTO the ProductDTO object to be converted.
     * @param categoryDTO the associated CategoryDTO object.
     * @return the converted Product entity.
     */
    private Product convertToEntity(ProductDTO productDTO, CategoryDTO categoryDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        // Setting the category information
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        product.setCategory(category);

        return product;
    }
    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        // ... (set other fields as necessary)

        return category;
    }

    /**
     * Converts a Product entity to a ProductDTO object.
     *
     * @param product the Product entity to be converted.
     * @return the converted ProductDTO object.
     */
    private ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        // Setting the category information
        Category category = product.getCategory();
        if (category != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId(category.getCategoryId());
            categoryDTO.setCategoryName(category.getCategoryName());
            //productDTO.setCategoryDTO(categoryDTO);
            productDTO.setCategoryId(product.getCategory().getCategoryId());
        }

        return productDTO;
    }

        public List<ProductDTO> getProductsByName(String name) {
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
//import com.application.nothing.dto.CategoryDTO;
//import com.application.nothing.dto.ProductDTO;
//import com.application.nothing.exception.ProductAlreadyExistsException;
//import com.application.nothing.exception.ProductNotFoundException;
//import com.application.nothing.model.Category;
//import com.application.nothing.model.Product;
//import com.application.nothing.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
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
//    private final CategoryService categoryService;
//
//    @Autowired
//    public ProductService(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    @Transactional(readOnly = true)
//    public List<ProductDTO> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<ProductDTO> getProductById(Long id) {
//        Optional<Product> product = productRepository.findById(id);
//        return product.map(this::convertToDto);
//    }
//
//    @Transactional
//    public ProductDTO createProduct(ProductDTO productDTO, CategoryDTO categoryDTO) {
//        if (productRepository.existsByName(productDTO.getName())) {
//            throw new ProductAlreadyExistsException("Product already exists with name: " + productDTO.getName());
//        }
//
//        Product product = convertToEntity(productDTO, categoryDTO);
//        Product savedProduct = productRepository.save(product);
//        return convertToDto(savedProduct);
//    }
//
//    @Transactional
//    public ProductDTO updateProduct(Long id, ProductDTO productDTO, CategoryDTO categoryDTO) {
//        Product existingProduct = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
//
//        updateProductProperties(existingProduct, productDTO, categoryDTO);
//        productDTO.setProductId(id);
//        Product updatedProduct = productRepository.save(convertToEntity(productDTO, categoryDTO));
//        return convertToDto(updatedProduct);
//    }
//
//
//    private void updateProductProperties(Product product, ProductDTO productDTO, CategoryDTO categoryDTO) {
//        product.setName(productDTO.getName());
//        product.setDescription(productDTO.getDescription());
//        product.setPrice(productDTO.getPrice());
//
//        // Update other properties of the product
//
//        if (categoryDTO != null) {
//            Category category = convertCategoryDTOToCategory(categoryDTO);
//            product.setCategory(category);
//        }
//    }
//
//    private Category convertCategoryDTOToCategory(CategoryDTO categoryDTO) {
//        Category category = new Category();
//        category.setCategoryId(categoryDTO.getCategoryId());
//        // Map other properties from CategoryDTO to Category
//        return category;
//    }
//
//    @Transactional
//    public void deleteById(Long id) {
//        if (!productRepository.existsById(id)) {
//            throw new ProductNotFoundException("Product not found with ID: " + id);
//        }
//        productRepository.deleteById(id);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ProductDTO> findByCategoryId(Long categoryId) {
//        List<Product> products = productRepository.findByCategory_categoryId(categoryId);
//        return products.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private Product convertToEntity(ProductDTO productDTO, CategoryDTO categoryDTO) {
//        Product product = new Product();
//        product.setProductId(productDTO.getProductId());
//        product.setName(productDTO.getName());
//        product.setDescription(productDTO.getDescription());
//        product.setPrice(productDTO.getPrice());
//
//        if (categoryDTO != null) {
//            Category category = new Category();
//            category.setCategoryId(categoryDTO.getCategoryId());
//            // Map other properties from CategoryDTO to Category
//            product.setCategory(category);
//        }
//
//        return product;
//    }
//
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
//    public List<ProductDTO> findByProductName(String name) {
//        List<Product> products = productRepository.findByName(name);
//        return products.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private ProductDTO convertToDTO(Product product) {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setProductId(product.getProductId());
//        productDTO.setName(product.getName());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setCreatedDate(product.getCreatedAt());
//        //    productDTO.setLastUpdatedDate(product.getLastUpdatedDate());
//        productDTO.setCategoryId(product.getCategory().getCategoryId());
//        return productDTO;
//
//    }
//}
//
