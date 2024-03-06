package com.virtusa.dlvery.common.Repository;

import com.virtusa.dlvery.common.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Category findByCategoryName(String categoryName);
    Category findByCategoryId(UUID categoryId);


}