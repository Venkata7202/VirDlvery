package com.virtusa.dlvery.common.Controller;

import com.virtusa.dlvery.Inventory.DTO.WarehouseDTO;
import com.virtusa.dlvery.common.DTO.ProductDTO;
import com.virtusa.dlvery.common.DTO.ProductRequestDTO;
import com.virtusa.dlvery.common.Repository.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String categoryName) {
        logger.info("Received request to get products by category: {}", categoryName);

        List<ProductDTO> products = productService.getProductsByCategory(categoryName);

        if (products.isEmpty()) {
            logger.info("No products found for category: {}", categoryName);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} products for category: {}", products.size(), categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/warehouse")
    public ResponseEntity<List<ProductDTO>> getProductsByWarehouse(@Validated @RequestBody WarehouseDTO warehouseDTO) {
        logger.info("Received request to get products by warehouse: {}", warehouseDTO);

        List<ProductDTO> products = productService.getProductsByWarehouse(warehouseDTO);

        if (products.isEmpty()) {
            logger.info("No products found for warehouse: {}", warehouseDTO);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} products for warehouse: {}", products.size(), warehouseDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/damaged/warehouse")
    public ResponseEntity<List<ProductDTO>> getDamagedProductsInWarehouse(@Validated @RequestBody WarehouseDTO warehouseDTO) {
        logger.info("Received request to get damaged products in warehouse: {}", warehouseDTO);

        List<ProductDTO> products = productService.getDamagedProductsInWarehouse(warehouseDTO);

        if (products.isEmpty()) {
            logger.info("No damaged products found in warehouse: {}", warehouseDTO);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} damaged products in warehouse: {}", products.size(), warehouseDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/perishable")
    public ResponseEntity<List<ProductDTO>> getPerishableProducts() {
        logger.info("Received request to get perishable products");

        List<ProductDTO> products = productService.getPerishableProducts();

        if (products.isEmpty()) {
            logger.info("No perishable products found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} perishable products", products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<ProductDTO>> getExpiredProducts(@RequestParam LocalDate expiryDate) {
        logger.info("Received request to get expired products before: {}", expiryDate);

        List<ProductDTO> products = productService.getExpiredProducts(expiryDate);

        if (products.isEmpty()) {
            logger.info("No expired products found before: {}", expiryDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} expired products before: {}", products.size(), expiryDate);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String productName) {
        logger.info("Received request to get product by name: {}", productName);

        ProductDTO product = productService.findProductByName(productName);

        if (product == null) {
            logger.info("No product found with name: {}", productName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning product: {}", productName);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        logger.info("Received request to get all products");

        List<ProductDTO> products = productService.getAllProducts();

        if (products.isEmpty()) {
            logger.info("No products found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductRequestDTO productDTO) {
        logger.info("Received request to create product: {}", productDTO.getProductName());

        ProductDTO createdProduct = productService.createProduct(productDTO);

        if (createdProduct == null) {
            logger.info("Product creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Product created successfully: {}", createdProduct.getProductName());
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable UUID productId,
            @Validated @RequestBody ProductRequestDTO updatedProductDTO) {
        logger.info("Received request to update product with ID: {}", productId);

        ProductDTO updatedProduct = productService.updateProduct(productId, updatedProductDTO);

        if (updatedProduct == null) {
            logger.info("Product update failed. No product found with ID: {}", productId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Product updated successfully: {}", updatedProduct.getProductName());
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        logger.info("Received request to delete product with ID: {}", productId);

        productService.deleteProduct(productId);

        logger.info("Product deleted successfully with ID: {}", productId);
        return ResponseEntity.noContent().build();
    }
}

