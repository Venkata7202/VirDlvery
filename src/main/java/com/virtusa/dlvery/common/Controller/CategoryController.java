package com.virtusa.dlvery.common.Controller;

import com.virtusa.dlvery.common.DTO.CategoryDTO;
import com.virtusa.dlvery.common.Repository.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
//@PreAuthorize("hasRole('Inventory')")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String categoryName) {
        logger.info("Received request to get category by name: {}", categoryName);

        CategoryDTO category = categoryService.findByCategoryName(categoryName);

        if (category == null) {
            logger.info("No category found with name: {}", categoryName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning category: {}", category.getCategoryName());
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        logger.info("Received request to get all categories");

        List<CategoryDTO> categories = categoryService.getAllCategories();

        if (categories.isEmpty()) {
            logger.info("No categories found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        logger.info("Received request to create category: {}", categoryDTO.getCategoryName());

        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);

        if (createdCategory == null) {
            logger.info("Category creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Category created successfully: {}", createdCategory.getCategoryName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable UUID categoryId,
            @Validated @RequestBody CategoryDTO updatedCategoryDTO) {
        logger.info("Received request to update category with ID: {}", categoryId);

        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, updatedCategoryDTO);

        if (updatedCategory == null) {
            logger.info("Category update failed. No category found with ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Category updated successfully: {}", updatedCategory.getCategoryName());
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        logger.info("Received request to delete category with ID: {}", categoryId);

        categoryService.deleteCategory(categoryId);

        logger.info("Category deleted successfully with ID: {}", categoryId);
        return ResponseEntity.noContent().build();
    }
}


