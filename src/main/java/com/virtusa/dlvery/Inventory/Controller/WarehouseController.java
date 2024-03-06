package com.virtusa.dlvery.Inventory.Controller;

import com.virtusa.dlvery.Inventory.DTO.WarehouseDTO;
import com.virtusa.dlvery.Inventory.DTO.WarehouseRequestDTO;
import com.virtusa.dlvery.Inventory.Repository.Service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/{warehouseName}")
    public ResponseEntity<WarehouseDTO> getWarehouseByName(@PathVariable String warehouseName) {
        logger.info("Received request to get warehouse by name: {}", warehouseName);

        WarehouseDTO warehouse = warehouseService.findByWarehouseName(warehouseName);

        if (warehouse == null) {
            logger.info("No warehouse found with name: {}", warehouseName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning warehouse: {}", warehouse.getWarehouseName());
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<WarehouseDTO>> getWarehousesByLocation(@PathVariable String location) {
        logger.info("Received request to get warehouses by location: {}", location);

        List<WarehouseDTO> warehouses = warehouseService.findByLocation(location);

        if (warehouses.isEmpty()) {
            logger.info("No warehouses found in location: {}", location);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} warehouses", warehouses.size());
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/capacityGreaterThan/{capacity}")
    public ResponseEntity<List<WarehouseDTO>> getWarehousesWithCapacityGreaterThan(@PathVariable int capacity) {
        logger.info("Received request to get warehouses with capacity greater than: {}", capacity);

        List<WarehouseDTO> warehouses = warehouseService.findByCapacityGreaterThan(capacity);

        if (warehouses.isEmpty()) {
            logger.info("No warehouses found with capacity greater than: {}", capacity);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} warehouses", warehouses.size());
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/capacityLessThan/{capacity}")
    public ResponseEntity<List<WarehouseDTO>> getWarehousesWithCapacityLessThan(@PathVariable int capacity) {
        logger.info("Received request to get warehouses with capacity less than: {}", capacity);

        List<WarehouseDTO> warehouses = warehouseService.findByCapacityLessThan(capacity);

        if (warehouses.isEmpty()) {
            logger.info("No warehouses found with capacity less than: {}", capacity);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} warehouses", warehouses.size());
        return ResponseEntity.ok(warehouses);
    }


//    @GetMapping("/withInventory")
//    public ResponseEntity<List<WarehouseDTO>> getWarehousesWithInventory() {
//        logger.info("Received request to get warehouses with inventory");
//
//        List<WarehouseDTO> warehouses = warehouseService.findByInventoryNotNull();
//
//        if (warehouses.isEmpty()) {
//            logger.info("No warehouses found with inventory");
//            return ResponseEntity.noContent().build();
//        }
//
//        logger.info("Returning {} warehouses", warehouses.size());
//        return ResponseEntity.ok(warehouses);
//    }

    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@Validated @RequestBody WarehouseRequestDTO warehouseDTO) {
        logger.info("Received request to create warehouse: {}", warehouseDTO.getWarehouseName());

        WarehouseDTO createdWarehouse = warehouseService.createWarehouse(warehouseDTO);

        if (createdWarehouse == null) {
            logger.info("Warehouse creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Warehouse created successfully: {}", createdWarehouse.getWarehouseName());
        return new ResponseEntity<>(createdWarehouse, HttpStatus.CREATED);
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(
            @PathVariable UUID warehouseId,
            @Validated @RequestBody WarehouseDTO updatedWarehouseDTO) {
        logger.info("Received request to update warehouse with ID: {}", warehouseId);

        WarehouseDTO updatedWarehouse = warehouseService.updateWarehouse(warehouseId, updatedWarehouseDTO);

        if (updatedWarehouse == null) {
            logger.info("Warehouse update failed. No warehouse found with ID: {}", warehouseId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Warehouse updated successfully: {}", updatedWarehouse.getWarehouseName());
        return ResponseEntity.ok(updatedWarehouse);
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable UUID warehouseId) {
        logger.info("Received request to delete warehouse with ID: {}", warehouseId);

        warehouseService.deleteWarehouse(warehouseId);

        logger.info("Warehouse deleted successfully with ID: {}", warehouseId);
        return ResponseEntity.noContent().build();
    }
}
