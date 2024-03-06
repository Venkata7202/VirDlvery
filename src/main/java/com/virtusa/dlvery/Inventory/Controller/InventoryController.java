package com.virtusa.dlvery.Inventory.Controller;

import com.virtusa.dlvery.Inventory.DTO.InventoryDTO;
import com.virtusa.dlvery.Inventory.DTO.InventoryRequestDTO;
import com.virtusa.dlvery.Inventory.Repository.Service.InventoryService;
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
@RequestMapping("/api/inventory")
public class InventoryController {

    private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByProductId(@PathVariable UUID productId) {
        logger.info("Received request to get inventory by product ID: {}", productId);

        List<InventoryDTO> inventory = inventoryService.getInventoryByProductId(productId);

        if (inventory.isEmpty()) {
            logger.info("No inventory found for product ID: {}", productId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory for product ID: {}", productId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByWarehouseId(@PathVariable UUID warehouseId) {
        logger.info("Received request to get inventory by warehouse ID: {}", warehouseId);

        List<InventoryDTO> inventory = inventoryService.getInventoryByWarehouseId(warehouseId);

        if (inventory.isEmpty()) {
            logger.info("No inventory found for warehouse ID: {}", warehouseId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory for warehouse ID: {}", warehouseId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/perishableBeforeExpiry/{expiryDate}")
    public ResponseEntity<List<InventoryDTO>> getPerishableInventoryBeforeExpiry(@PathVariable String expiryDate) {
        logger.info("Received request to get perishable inventory before expiry date: {}", expiryDate);

        LocalDate parsedExpiryDate = LocalDate.parse(expiryDate);
        List<InventoryDTO> perishableInventory = inventoryService.getPerishableInventoryBeforeExpiry(parsedExpiryDate);

        if (perishableInventory.isEmpty()) {
            logger.info("No perishable inventory found before expiry date: {}", expiryDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning perishable inventory before expiry date: {}", expiryDate);
        return ResponseEntity.ok(perishableInventory);
    }


    @GetMapping("/quantity/{quantityIn}/{quantityOut}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByQuantity(
            @PathVariable Long quantityIn,
            @PathVariable Long quantityOut) {
        logger.info("Received request to get inventory by quantity: quantityIn={}, quantityOut={}", quantityIn, quantityOut);

        List<InventoryDTO> inventoryList = inventoryService.getInventoryByQuantity(quantityIn, quantityOut);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for the given quantity criteria");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory by quantity criteria");
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/productAndWarehouse/{productId}/{warehouseId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByProductIdAndWarehouseId(
            @PathVariable UUID productId,
            @PathVariable UUID warehouseId) {
        logger.info("Received request to get inventory by product ID: {} and warehouse ID: {}", productId, warehouseId);

        List<InventoryDTO> inventoryList = inventoryService.getInventoryByProductIdAndWarehouseId(productId, warehouseId);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {} and warehouse ID: {}", productId, warehouseId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory by product ID and warehouse ID");
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/productAndPerishable/{productId}/{perishable}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByProductIdAndPerishable(
            @PathVariable UUID productId,
            @PathVariable boolean perishable) {
        logger.info("Received request to get inventory by product ID: {} and perishable status: {}", productId, perishable);

        List<InventoryDTO> inventoryList = inventoryService.getInventoryByProductIdAndPerishable(productId, perishable);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {} and perishable status: {}", productId, perishable);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory by product ID and perishable status");
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/productAndExpiryDate/{productId}/{expiryDate}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByProductIdAndExpiryDate(
            @PathVariable UUID productId,
            @PathVariable String expiryDate) {
        logger.info("Received request to get inventory by product ID: {} and expiry date: {}", productId, expiryDate);

        LocalDate parsedExpiryDate = LocalDate.parse(expiryDate);
        List<InventoryDTO> inventoryList = inventoryService.getInventoryByProductIdAndExpiryDate(productId, parsedExpiryDate);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for product ID: {} and expiry date: {}", productId, expiryDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory by product ID and expiry date");
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/warehouseAndDamagedStatus/{warehouseId}/{damagedStatus}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByWarehouseIdAndDamagedStatus(
            @PathVariable UUID warehouseId,
            @PathVariable boolean damagedStatus) {
        logger.info("Received request to get inventory by warehouse ID: {} and damaged status: {}", warehouseId, damagedStatus);

        List<InventoryDTO> inventoryList = inventoryService.getInventoryByWarehouseIdAndDamagedStatus(warehouseId, damagedStatus);

        if (inventoryList.isEmpty()) {
            logger.info("No inventory found for warehouse ID: {} and damaged status: {}", warehouseId, damagedStatus);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning inventory by warehouse ID and damaged status");
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/perishableAndExpiryDate/{perishable}/{expiryDate}")
    public ResponseEntity<List<InventoryDTO>> getPerishableInventoryBeforeExpiry(
            @PathVariable boolean perishable,
            @PathVariable String expiryDate) {
        logger.info("Received request to get perishable inventory before expiry date: {} and perishable: {}", expiryDate, perishable);

        LocalDate parsedExpiryDate = LocalDate.parse(expiryDate);
        List<InventoryDTO> perishableInventory = inventoryService.getPerishableInventoryBeforeExpiry(perishable, parsedExpiryDate);

        if (perishableInventory.isEmpty()) {
            logger.info("No perishable inventory found before expiry date: {} and perishable: {}", expiryDate, perishable);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning perishable inventory before expiry date and perishable status");
        return ResponseEntity.ok(perishableInventory);
    }

    @GetMapping("/nonExpiredInventory/{productId}/{warehouseId}/{currentDate}")
    public ResponseEntity<List<InventoryDTO>> getNonExpiredInventory(
            @PathVariable UUID productId,
            @PathVariable UUID warehouseId,
            @PathVariable String currentDate) {
        logger.info("Received request to get non-expired inventory for product ID: {}, warehouse ID: {} and current date: {}", productId, warehouseId, currentDate);

        LocalDate parsedCurrentDate = LocalDate.parse(currentDate);
        List<InventoryDTO> nonExpiredInventory = inventoryService.getNonExpiredInventory(productId, warehouseId, parsedCurrentDate);

        if (nonExpiredInventory.isEmpty()) {
            logger.info("No non-expired inventory found for product ID: {}, warehouse ID: {} and current date: {}", productId, warehouseId, currentDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning non-expired inventory for product ID, warehouse ID, and current date");
        return ResponseEntity.ok(nonExpiredInventory);
    }

    @GetMapping("/perishable/{perishable}")
    public ResponseEntity<List<InventoryDTO>> getPerishableInventory(@PathVariable boolean perishable) {
        logger.info("Received request to get perishable inventory with perishable status: {}", perishable);

        List<InventoryDTO> perishableInventory = inventoryService.getPerishableInventory(perishable);

        if (perishableInventory.isEmpty()) {
            logger.info("No perishable inventory found with perishable status: {}", perishable);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning perishable inventory with perishable status");
        return ResponseEntity.ok(perishableInventory);
    }

    @GetMapping("/damagedInventory/{damagedStatus}")
    public ResponseEntity<List<InventoryDTO>> getDamagedInventory(@PathVariable boolean damagedStatus) {
        logger.info("Received request to get damaged inventory with damaged status: {}", damagedStatus);

        List<InventoryDTO> damagedInventory = inventoryService.getDamagedInventory(damagedStatus);

        if (damagedInventory.isEmpty()) {
            logger.info("No damaged inventory found with damaged status: {}", damagedStatus);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning damaged inventory with damaged status");
        return ResponseEntity.ok(damagedInventory);
    }

    @GetMapping("/expiredInventoryBefore/{date}")
    public ResponseEntity<List<InventoryDTO>> getExpiredInventoryBefore(@PathVariable String date) {
        logger.info("Received request to get expired inventory before date: {}", date);

        LocalDate parsedDate = LocalDate.parse(date);
        List<InventoryDTO> expiredInventory = inventoryService.getExpiredInventoryBefore(parsedDate);

        if (expiredInventory.isEmpty()) {
            logger.info("No expired inventory found before date: {}", date);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning expired inventory before date");
        return ResponseEntity.ok(expiredInventory);
    }

    @GetMapping("/expiredInventoryAfter/{date}")
    public ResponseEntity<List<InventoryDTO>> getExpiredInventoryAfter(@PathVariable String date) {
        logger.info("Received request to get expired inventory after date: {}", date);

        LocalDate parsedDate = LocalDate.parse(date);
        List<InventoryDTO> expiredInventory = inventoryService.getExpiredInventoryAfter(parsedDate);

        if (expiredInventory.isEmpty()) {
            logger.info("No expired inventory found after date: {}", date);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning expired inventory after date");
        return ResponseEntity.ok(expiredInventory);
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@Validated @RequestBody InventoryRequestDTO inventoryDTO) {
        logger.info("Received request to create inventory for product ID: {}", inventoryDTO.getProductId());

        InventoryDTO createdInventory = inventoryService.createInventory(inventoryDTO);

        if (createdInventory == null) {
            logger.info("Inventory creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Inventory created successfully for product ID: {}", createdInventory.getProduct().getProductId());
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryDTO> updateInventory(
            @PathVariable UUID inventoryId,
            @Validated @RequestBody InventoryRequestDTO updatedInventoryDTO) {
        logger.info("Received request to update inventory with ID: {}", inventoryId);

        InventoryDTO updatedInventory = inventoryService.updateInventory(inventoryId, updatedInventoryDTO);

        if (updatedInventory == null) {
            logger.info("Inventory update failed. No inventory found with ID: {}", inventoryId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Inventory updated successfully with ID: {}", updatedInventory.getMovementId());
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID inventoryId) {
        logger.info("Received request to delete inventory with ID: {}", inventoryId);

        inventoryService.deleteInventory(inventoryId);

        logger.info("Inventory deleted successfully with ID: {}", inventoryId);
        return ResponseEntity.noContent().build();
    }
}
