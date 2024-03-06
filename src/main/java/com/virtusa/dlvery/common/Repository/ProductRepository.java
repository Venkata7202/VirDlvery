package com.virtusa.dlvery.common.Repository;

import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import com.virtusa.dlvery.common.Entities.Category;
import com.virtusa.dlvery.common.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByWarehouse(Warehouse warehouse);

    List<Product> findByCategory(Category category);

    List<Product> findByIsDamaged(boolean isDamaged);

    List<Product> findByIsPerishable(boolean isPerishable);

    List<Product> findByExpiryDateBefore(LocalDate expiryDate);

    @Query("SELECT p FROM Product p WHERE p.warehouse = :warehouse AND p.isDamaged = :isDamaged")
    List<Product> findDamagedProductsInWarehouse(@Param("warehouse") Warehouse warehouse,
                                                 @Param("isDamaged") boolean isDamaged);

    @Query("SELECT p FROM Product p WHERE p.expiryDate < :expiryDate")
    List<Product> findExpiredProducts(@Param("expiryDate") LocalDate expiryDate);

    Product findByProductName(String productName);

    Product findByProductId(UUID productId);
}
