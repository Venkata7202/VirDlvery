package com.virtusa.dlvery.Inventory.Repository;

import com.virtusa.dlvery.Inventory.Entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    List<Inventory> findByProduct_ProductId(UUID productId);

    List<Inventory> findByWarehouse_WarehouseId(UUID warehouseId);

    List<Inventory> findByProduct_IsPerishableIsTrueAndDateBefore(LocalDate expiryDate);


    List<Inventory> findByQuantityInGreaterThanAndQuantityOutLessThan(Long quantityIn, Long quantityOut);

    List<Inventory> findByProduct_ProductIdAndWarehouse_WarehouseId(UUID productId, UUID warehouseId);

    List<Inventory> findByProduct_ProductIdAndProduct_IsPerishable(UUID productId, boolean perishable);

    List<Inventory> findByProduct_ProductIdAndProduct_ExpiryDate(UUID productId, LocalDate expiryDate);

    List<Inventory> findByWarehouse_WarehouseIdAndProduct_IsDamaged(UUID warehouseId, boolean damagedStatus);

    List<Inventory> findByProduct_IsPerishableAndProduct_ExpiryDate(boolean perishable, LocalDate expiryDate);

    default List<Inventory> findNonExpiredInventory(@Param("productId") UUID productId,
                                                       @Param("warehouseId") UUID warehouseId,
                                                       @Param("currentDate") LocalDate currentDate) {
        // Custom logic to find non-expired inventory items
        List<Inventory> InventoryList = null;
        // Implement your custom logic here
        return InventoryList;
    }

    List<Inventory> findByProduct_IsPerishable(boolean perishable);

    List<Inventory> findByProduct_IsDamaged(boolean damagedStatus);

    List<Inventory> findByProduct_ExpiryDateBefore(LocalDate date);

    List<Inventory> findByProductExpiryDateAfter(LocalDate date);

//    List<Inventory> findByQuantityInEqualsAndQuantityOut(Long quantityIn, Long quantityOut);
}
