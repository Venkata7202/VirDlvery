package com.virtusa.dlvery.Inventory.Repository.Service;

import com.virtusa.dlvery.Inventory.DTO.WarehouseDTO;
import com.virtusa.dlvery.Inventory.DTO.WarehouseRequestDTO;
import com.virtusa.dlvery.Inventory.Entities.Inventory;
import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import com.virtusa.dlvery.Inventory.Repository.InventoryRepository;
import com.virtusa.dlvery.Inventory.Repository.WarehouseRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class WarehouseService {

    private final Logger logger = LoggerFactory.getLogger(WarehouseService.class);

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    InventoryRepository inventoryRepository;
    public WarehouseDTO findByWarehouseName(String warehouseName) {
        logger.info("Finding warehouse by name: {}", warehouseName);

        if (warehouseName == null) {
            logger.error("Warehouse name is null. Unable to find warehouse.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        List<Warehouse> warehouses = warehouseRepository.findByWarehouseName(warehouseName);

        if (warehouses.isEmpty()) {
            logger.info("No warehouse found with name: {}", warehouseName);
            // Handle case when no warehouse is found, return null/empty DTO
            return null;
        }

        // Assuming you want to return the first warehouse if multiple found with the same name
        return DTOConversionUtil.convertToWarehouseDTO(warehouses.get(0));
    }

    public List<WarehouseDTO> findByLocation(String location) {
        logger.info("Finding warehouses by location: {}", location);

        if (location == null) {
            logger.error("Location is null. Unable to find warehouses.");
            // Handle error appropriately, throw exception or return empty list
            return List.of();
        }

        List<Warehouse> warehouses = warehouseRepository.findByLocation(location);

        if (warehouses.isEmpty()) {
            logger.info("No warehouses found in location: {}", location);
            // Handle case when no warehouses are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToWarehouseDTOList(warehouses);
    }

    public List<WarehouseDTO> findByCapacityGreaterThan(int capacity) {
        logger.info("Finding warehouses with capacity greater than: {}", capacity);

        List<Warehouse> warehouses = warehouseRepository.findByCapacityGreaterThan(capacity);

        if (warehouses.isEmpty()) {
            logger.info("No warehouses found with capacity greater than: {}", capacity);
            // Handle case when no warehouses are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToWarehouseDTOList(warehouses);
    }

    public List<WarehouseDTO> findByCapacityLessThan(int capacity) {
        logger.info("Finding warehouses with capacity less than: {}", capacity);

        List<Warehouse> warehouses = warehouseRepository.findByCapacityLessThan(capacity);

        if (warehouses.isEmpty()) {
            logger.info("No warehouses found with capacity less than: {}", capacity);
            // Handle case when no warehouses are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToWarehouseDTOList(warehouses);
    }

//    public List<WarehouseDTO> findByProductsNotNull() {
//        logger.info("Finding warehouses with products");
//
//        List<Warehouse> warehouses = warehouseRepository.findByProductsIsNotNull();
//
//        if (warehouses.isEmpty()) {
//            logger.info("No warehouses found with products");
//            // Handle case when no warehouses are found, return empty list
//            return List.of();
//        }
//
//        return DTOConversionUtil.convertToWarehouseDTOList(warehouses);
//    }


    public WarehouseDTO createWarehouse(WarehouseRequestDTO warehouseDTO) {
        logger.info("Creating warehouse: {}", warehouseDTO.getWarehouseName());

        if (warehouseDTO.getWarehouseName() == null) {
            logger.error("Warehouse name is null. Unable to create warehouse.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Warehouse warehouse = DTOConversionUtil.convertToWarehouse(warehouseDTO);
        warehouse.setWarehouseId(UUID.randomUUID());

        List<Inventory> inventories = inventoryRepository.findByWarehouse_WarehouseId(warehouse.getWarehouseId());
        warehouse.setInventory((Set<Inventory>) inventories);

        // Save warehouse
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToWarehouseDTO(savedWarehouse);
    }

    public WarehouseDTO updateWarehouse(UUID warehouseId, WarehouseDTO updatedWarehouseDTO) {
        logger.info("Updating warehouse with ID: {}", warehouseId);

        // Check if warehouse with given ID exists
        if (!warehouseRepository.existsById(warehouseId)) {
            logger.info("No warehouse found with ID: {}", warehouseId);
            // Handle case when no warehouse is found, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Warehouse updatedWarehouse = DTOConversionUtil.convertToWarehouse(updatedWarehouseDTO);

        // Set the ID for the existing warehouse
        updatedWarehouse.setWarehouseId(warehouseId);
        updatedWarehouse.setWarehouseName(updatedWarehouseDTO.getWarehouseName());
        updatedWarehouse.setCapacity(updatedWarehouseDTO.getCapacity());
        updatedWarehouse.setLocation(updatedWarehouseDTO.getLocation());

        List<Inventory> inventories = inventoryRepository.findByWarehouse_WarehouseId(updatedWarehouseDTO.getWarehouseId());
        updatedWarehouse.setInventory((Set<Inventory>) inventories);
        // Save updated warehouse
        Warehouse savedWarehouse = warehouseRepository.save(updatedWarehouse);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToWarehouseDTO(savedWarehouse);
    }

    public void deleteWarehouse(UUID warehouseId) {
        logger.info("Deleting warehouse with ID: {}", warehouseId);
        warehouseRepository.deleteById(warehouseId);
    }


}
