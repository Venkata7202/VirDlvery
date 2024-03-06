package com.virtusa.dlvery.Inventory.Repository.Service;

import com.virtusa.dlvery.Inventory.DTO.InventoryDTO;
import com.virtusa.dlvery.Inventory.DTO.InventoryRequestDTO;
import com.virtusa.dlvery.Inventory.Entities.Inventory;
import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import com.virtusa.dlvery.Inventory.Repository.InventoryRepository;
import com.virtusa.dlvery.Inventory.Repository.WarehouseRepository;
import com.virtusa.dlvery.common.Entities.Product;
import com.virtusa.dlvery.common.Repository.ProductRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<InventoryDTO> getInventoryByProductId(UUID productId) {
        logger.info("Fetching inventory by product ID: {}", productId);

        if (productId == null) {
            logger.error("Product ID is null. Unable to fetch inventory.");
            return List.of();
        }

        List<Inventory> inventoryList = inventoryRepository.findByProduct_ProductId(productId);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {}", productId);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getInventoryByWarehouseId(UUID warehouseId) {
        logger.info("Fetching inventory by warehouse ID: {}", warehouseId);

        if (warehouseId == null) {
            logger.error("Warehouse ID is null. Unable to fetch inventory.");
            return List.of();
        }

        List<Inventory> inventoryList = inventoryRepository.findByWarehouse_WarehouseId(warehouseId);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for warehouse ID: {}", warehouseId);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getPerishableInventoryBeforeExpiry(LocalDate expiryDate) {
        logger.info("Fetching perishable inventory before expiry date: {}", expiryDate);

        if (expiryDate == null) {
            logger.error("Expiry date is null. Unable to fetch perishable inventory.");
            return List.of();
        }

        List<Inventory> perishableInventory = inventoryRepository.findByProduct_IsPerishableIsTrueAndDateBefore(expiryDate);

        if (perishableInventory.isEmpty()) {
            logger.info("No perishable inventory found before expiry date: {}", expiryDate);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(perishableInventory);
    }



    public List<InventoryDTO> getInventoryByQuantity(Long quantityIn, Long quantityOut) {
        logger.info("Fetching inventory by quantity: quantityIn={}, quantityOut={}", quantityIn, quantityOut);

        List<Inventory> inventoryList = inventoryRepository.findByQuantityInGreaterThanAndQuantityOutLessThan(quantityIn, quantityOut);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for the given quantity criteria.");
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getInventoryByProductIdAndWarehouseId(UUID productId, UUID warehouseId) {
        logger.info("Fetching inventory by product ID: {} and warehouse ID: {}", productId, warehouseId);

        if (productId == null || warehouseId == null) {
            logger.error("Product ID or Warehouse ID is null. Unable to fetch inventory.");
            return List.of();
        }

        List<Inventory> inventoryList = inventoryRepository.findByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {} and warehouse ID: {}", productId, warehouseId);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getInventoryByProductIdAndPerishable(UUID productId, boolean perishable) {
        logger.info("Fetching inventory by product ID: {} and perishable status: {}", productId, perishable);

        List<Inventory> inventoryList = inventoryRepository.findByProduct_ProductIdAndProduct_IsPerishable(productId, perishable);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {} and perishable status: {}", productId, perishable);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getInventoryByProductIdAndExpiryDate(UUID productId, LocalDate expiryDate) {
        logger.info("Fetching inventory by product ID: {} and expiry date: {}", productId, expiryDate);

        if (productId == null || expiryDate == null) {
            logger.error("Product ID or Expiry Date is null. Unable to fetch inventory.");
            return List.of();
        }

        List<Inventory> inventoryList = inventoryRepository.findByProduct_ProductIdAndProduct_ExpiryDate(productId, expiryDate);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {} and expiry date: {}", productId, expiryDate);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getInventoryByWarehouseIdAndDamagedStatus(UUID warehouseId, boolean damagedStatus) {
        logger.info("Fetching inventory by warehouse ID: {} and damaged status: {}", warehouseId, damagedStatus);

        if (warehouseId == null) {
            logger.error("Warehouse ID is null. Unable to fetch inventory.");
            return List.of();
        }

        List<Inventory> inventoryList = inventoryRepository.findByWarehouse_WarehouseIdAndProduct_IsDamaged(warehouseId, damagedStatus);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for warehouse ID: {} and damaged status: {}", warehouseId, damagedStatus);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(inventoryList);
    }

    public List<InventoryDTO> getPerishableInventoryBeforeExpiry(boolean perishable, LocalDate expiryDate) {
        logger.info("Fetching perishable inventory before expiry date: {} and perishable: {}", expiryDate, perishable);

        List<Inventory> perishableInventory = inventoryRepository.findByProduct_IsPerishableAndProduct_ExpiryDate(perishable, expiryDate);

        if (perishableInventory.isEmpty()) {
            logger.info("No perishable inventory found before expiry date: {} and perishable: {}", expiryDate, perishable);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(perishableInventory);
    }

    public List<InventoryDTO> getNonExpiredInventory(UUID productId, UUID warehouseId, LocalDate currentDate) {
        logger.info("Fetching non-expired inventory for product ID: {}, warehouse ID: {} and current date: {}", productId, warehouseId, currentDate);

        // You need to implement your custom logic in the repository interface for this method
        List<Inventory> nonExpiredInventory = inventoryRepository.findNonExpiredInventory(productId, warehouseId, currentDate);

        if (nonExpiredInventory.isEmpty()) {
            logger.info("No non-expired inventory found for product ID: {}, warehouse ID: {} and current date: {}", productId, warehouseId, currentDate);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(nonExpiredInventory);
    }

    public List<InventoryDTO> getPerishableInventory(boolean perishable) {
        logger.info("Fetching perishable inventory with perishable status: {}", perishable);

        List<Inventory> perishableInventory = inventoryRepository.findByProduct_IsPerishable(perishable);

        if (perishableInventory.isEmpty()) {
            logger.info("No perishable inventory found with perishable status: {}", perishable);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(perishableInventory);
    }

    public List<InventoryDTO> getDamagedInventory(boolean damagedStatus) {
        logger.info("Fetching damaged inventory with damaged status: {}", damagedStatus);

        List<Inventory> damagedInventory = inventoryRepository.findByProduct_IsDamaged(damagedStatus);

        if (damagedInventory.isEmpty()) {
            logger.info("No damaged inventory found with damaged status: {}", damagedStatus);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(damagedInventory);
    }

    public List<InventoryDTO> getExpiredInventoryBefore(LocalDate date) {
        logger.info("Fetching expired inventory before date: {}", date);

        List<Inventory> expiredInventory = inventoryRepository.findByProduct_ExpiryDateBefore(date);

        if (expiredInventory.isEmpty()) {
            logger.info("No expired inventory found before date: {}", date);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(expiredInventory);
    }

    public List<InventoryDTO> getExpiredInventoryAfter(LocalDate date) {
        logger.info("Fetching expired inventory after date: {}", date);

        List<Inventory> expiredInventory = inventoryRepository.findByProductExpiryDateAfter(date);

        if (expiredInventory.isEmpty()) {
            logger.info("No expired inventory found after date: {}", date);
            return List.of();
        }

        return DTOConversionUtil.convertToInventoryDTOList(expiredInventory);
    }

    public InventoryDTO createInventory(InventoryRequestDTO inventoryDTO) {
        logger.info("Creating inventory for product ID: {}", inventoryDTO.getProductId());

        if (inventoryDTO.getProductId() == null) {
            logger.error("Product ID is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Product product = productRepository.findByProductId(inventoryDTO.getProductId());

        if (product == null) {
            logger.error("Product is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }


        if (inventoryDTO.getWarehouseId() == null) {
            logger.error("Warehouse ID is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Warehouse warehouse = warehouseRepository.findByWarehouseId(inventoryDTO.getWarehouseId());

        if (warehouse == null) {
            logger.error("Warehouse is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Inventory inventory = DTOConversionUtil.convertToInventory(inventoryDTO, product, warehouse);

        inventory.setMovementId(UUID.randomUUID());
        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);
        // Save inventory
        Inventory savedInventory = inventoryRepository.save(inventory);



        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToInventoryDTO(savedInventory);
    }

    public InventoryDTO updateInventory(UUID inventoryId, InventoryRequestDTO updatedInventoryDTO) {
        logger.info("Updating inventory with ID: {}", inventoryId);

        // Check if inventory with given ID exists
        if (!inventoryRepository.existsById(inventoryId)) {
            logger.info("No inventory found with ID: {}", inventoryId);
            // Handle case when no inventory is found, return null/empty DTO
            return null;
        }


        if (updatedInventoryDTO.getProductId() == null) {
            logger.error("Product ID is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Product product = productRepository.findByProductId(updatedInventoryDTO.getProductId());

        if (product == null) {
            logger.error("Product is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }


        if (updatedInventoryDTO.getWarehouseId() == null) {
            logger.error("Warehouse ID is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Warehouse warehouse = warehouseRepository.findByWarehouseId(updatedInventoryDTO.getWarehouseId());

        if (warehouse == null) {
            logger.error("Warehouse is null. Unable to create inventory.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }


        // Convert DTO to entity
        Inventory updatedInventory = DTOConversionUtil.convertToInventory(updatedInventoryDTO, product, warehouse);

        // Set the ID for the existing inventory
        updatedInventory.setMovementId(inventoryId);
        updatedInventory.setDate(updatedInventoryDTO.getDate());
        updatedInventory.setReason(updatedInventory.getReason());
        updatedInventory.setMovementType(updatedInventoryDTO.getMovementType());
        updatedInventory.setQuantityIn(updatedInventory.getQuantityIn());
        updatedInventory.setQuantityOut(updatedInventoryDTO.getQuantityOut());
        updatedInventory.setWarehouse(warehouse);
        updatedInventory.setProduct(product);

        // Save updated inventory
        Inventory savedInventory = inventoryRepository.save(updatedInventory);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToInventoryDTO(savedInventory);
    }

    public void deleteInventory(UUID inventoryId) {
        logger.info("Deleting inventory with ID: {}", inventoryId);
        inventoryRepository.deleteById(inventoryId);
    }

}
