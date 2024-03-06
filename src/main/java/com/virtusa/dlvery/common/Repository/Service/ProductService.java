package com.virtusa.dlvery.common.Repository.Service;

import com.virtusa.dlvery.Inventory.DTO.WarehouseDTO;
import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import com.virtusa.dlvery.Inventory.Repository.WarehouseRepository;
import com.virtusa.dlvery.common.DTO.CategoryDTO;
import com.virtusa.dlvery.common.DTO.ProductDTO;
import com.virtusa.dlvery.common.DTO.ProductRequestDTO;
import com.virtusa.dlvery.common.Entities.Category;
import com.virtusa.dlvery.common.Entities.Product;
import com.virtusa.dlvery.common.Repository.CategoryRepository;
import com.virtusa.dlvery.common.Repository.ProductRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CategoryRepository categoryService;

    public List<ProductDTO> getProductsByCategory(String categoryName) {
        logger.info("Fetching products by category: {}", categoryName);

        if (categoryName == null) {
            logger.error("Category name is null. Unable to fetch products.");
            // Handle error appropriately, throw exception or return empty list
            return List.of();
        }

        CategoryDTO category = DTOConversionUtil.convertToCategoryDTO(categoryService.findByCategoryName(categoryName));

        if (category == null) {
            logger.info("No category found with name: {}", categoryName);
            // Handle case when no category is found, return empty list
            return List.of();
        }

        List<Product> products = productRepository.findByCategory(DTOConversionUtil.convertToCategory(category));

        if (products.isEmpty()) {
            logger.info("No products found for category: {}", categoryName);
            // Handle case when no products are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToProductDTOList(products);
    }

    public List<ProductDTO> getProductsByWarehouse(WarehouseDTO warehouseDTO) {
        logger.info("Fetching products by warehouse: {}", warehouseDTO);

        if (warehouseDTO == null) {
            logger.error("WarehouseDTO is null. Unable to fetch products.");
            // Handle error appropriately, throw exception or return empty list
            return List.of();
        }

        // Assuming you have a method in DTOConversionUtil to convert WarehouseDTO to Warehouse
        Warehouse warehouse = DTOConversionUtil.convertToWarehouse(warehouseDTO);

        List<Product> products = productRepository.findByWarehouse(warehouse);

        if (products.isEmpty()) {
            logger.info("No products found for warehouse: {}", warehouseDTO);
            // Handle case when no products are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToProductDTOList(products);
    }

    public List<ProductDTO> getDamagedProductsInWarehouse(WarehouseDTO warehouseDTO) {
        logger.info("Fetching damaged products in warehouse: {}", warehouseDTO);

        if (warehouseDTO == null) {
            logger.error("WarehouseDTO is null. Unable to fetch damaged products.");
            // Handle error appropriately, throw exception or return empty list
            return List.of();
        }

        // Assuming you have a method in DTOConversionUtil to convert WarehouseDTO to Warehouse
        Warehouse warehouse = DTOConversionUtil.convertToWarehouse(warehouseDTO);

        List<Product> damagedProducts = productRepository.findDamagedProductsInWarehouse(warehouse, true);

        if (damagedProducts.isEmpty()) {
            logger.info("No damaged products found in warehouse: {}", warehouseDTO);
            // Handle case when no damaged products are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToProductDTOList(damagedProducts);
    }


    public List<ProductDTO> getPerishableProducts() {
        logger.info("Fetching perishable products.");

        List<Product> perishableProducts = productRepository.findByIsPerishable(true);

        if (perishableProducts.isEmpty()) {
            logger.info("No perishable products found.");
            // Handle case when no perishable products are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToProductDTOList(perishableProducts);
    }

    public List<ProductDTO> getExpiredProducts(LocalDate expiryDate) {
        logger.info("Fetching expired products before: {}", expiryDate);

        if (expiryDate == null) {
            logger.error("Expiry date is null. Unable to fetch expired products.");
            // Handle error appropriately, throw exception or return empty list
            return List.of();
        }

        List<Product> expiredProducts = productRepository.findExpiredProducts(expiryDate);

        if (expiredProducts.isEmpty()) {
            logger.info("No expired products found before: {}", expiryDate);
            // Handle case when no expired products are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToProductDTOList(expiredProducts);
    }

    public ProductDTO findProductByName(String productName) {
        logger.info("Finding product by name: {}", productName);

        if (productName == null) {
            logger.error("Product name is null. Unable to find product.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Product product = productRepository.findByProductName(productName);

        if (product == null) {
            logger.info("No product found with name: {}", productName);
            // Handle case when no product is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToProductDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        return DTOConversionUtil.convertToProductDTOList(products);
    }

    public ProductDTO createProduct(ProductRequestDTO productDTO) {
        logger.info("Creating product: {}", productDTO.getProductName());

        if (productDTO.getProductName() == null) {
            logger.error("Product name is null. Unable to create product.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Check if the product with the same name already exists
        Product existingProduct = productRepository.findByProductName(productDTO.getProductName());


        if (existingProduct != null) {
            logger.info("Product with name '{}' already exists. Unable to create duplicate product.", productDTO.getProductName());
            // Handle case when the product already exists, return null/empty DTO
            return null;
        }

        Category category = categoryService.findByCategoryId(productDTO.getCategoryId());
        if (category == null) {
            logger.info("category with id '{}' Not exists. Unable to create product.", productDTO.getCategoryId());
            // Handle case when the product already exists, return null/empty DTO
            return null;
        }


        // Convert DTO to entity
        Product product = DTOConversionUtil.convertToProduct(productDTO, category);
        product.setProductId(UUID.randomUUID());
        product.setProductName(product.getProductName().toLowerCase());

        // Save product
        Product savedProduct = productRepository.save(product);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(UUID productId, ProductRequestDTO updatedProductDTO) {
        logger.info("Updating product with ID: {}", productId);

        // Check if product with given ID exists
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            logger.info("No product found with ID: {}", productId);
            // Handle case when no product is found, return null/empty DTO
            return null;
        }

        // Get the existing product
        Product existingProduct = optionalProduct.get();

        // Update the existing product with the new data
        existingProduct.setProductName(updatedProductDTO.getProductName().toLowerCase());
        existingProduct.setCategory(categoryService.findByCategoryId(updatedProductDTO.getCategoryId()));
        existingProduct.setPerishable(updatedProductDTO.isPerishable());
        existingProduct.setExpiryDate(updatedProductDTO.getExpiryDate());
        existingProduct.setDamaged(updatedProductDTO.isDamaged());
        existingProduct.setWarehouse(warehouseRepository.findByWarehouseId(updatedProductDTO.getWarehouseId()));

        // Save updated product
        Product savedProduct = productRepository.save(existingProduct);


        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToProductDTO(savedProduct);
    }

    public void deleteProduct(UUID productId) {
        logger.info("Deleting product with ID: {}", productId);
        productRepository.deleteById(productId);
    }
}

