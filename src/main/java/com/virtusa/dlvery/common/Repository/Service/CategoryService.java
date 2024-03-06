package com.virtusa.dlvery.common.Repository.Service;

import com.virtusa.dlvery.common.DTO.CategoryDTO;
import com.virtusa.dlvery.common.Entities.Category;
import com.virtusa.dlvery.common.Repository.CategoryRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO findByCategoryName(String categoryName) {
        logger.info("Finding category by name: {}", categoryName);

        if (categoryName == null) {
            logger.error("Category name is null. Unable to find category.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Category category = categoryRepository.findByCategoryName(categoryName.toLowerCase());

        if (category == null) {
            logger.info("No category found with name: {}", categoryName);
            // Handle case when no category is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToCategoryDTO(category);
    }



    public List<CategoryDTO> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        return DTOConversionUtil.convertToCategoryDTOList(categories);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        logger.info("Creating category: {}", categoryDTO.getCategoryName());

        if (categoryDTO.getCategoryName() == null) {
            logger.error("Category name is null. Unable to create category.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Check if the category with the same name already exists
        Category existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if (existingCategory != null) {
            logger.info("Category with name '{}' already exists. Unable to create duplicate category.", categoryDTO.getCategoryName());
            // Handle case when the category already exists, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Category category = DTOConversionUtil.convertToCategory(categoryDTO);
        category.setCategoryId(UUID.randomUUID());
        category.setCategoryName(category.getCategoryName().toLowerCase());

        // Save category
        Category savedCategory = categoryRepository.save(category);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToCategoryDTO(savedCategory);
    }


    public CategoryDTO updateCategory(UUID categoryId, CategoryDTO updatedCategoryDTO) {
        logger.info("Updating category with ID: {}", categoryId);

        // Check if category with given ID exists
        if (!categoryRepository.existsById(categoryId)) {
            logger.info("No category found with ID: {}", categoryId);
            // Handle case when no category is found, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Category updatedCategory = DTOConversionUtil.convertToCategory(updatedCategoryDTO);

        // Set the ID for the existing category
        updatedCategory.setCategoryId(categoryId);
        updatedCategory.setCategoryName(updatedCategoryDTO.getCategoryName().toLowerCase());

        // Save updated category
        Category savedCategory = categoryRepository.save(updatedCategory);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToCategoryDTO(savedCategory);
    }

    public void deleteCategory(UUID categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);
        categoryRepository.deleteById(categoryId);
    }

}

