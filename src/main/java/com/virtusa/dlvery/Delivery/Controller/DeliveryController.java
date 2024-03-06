package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryDTO;
import com.virtusa.dlvery.Delivery.Enum.DeliveryStatus;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryService;
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
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable UUID deliveryId) {
        logger.info("Received request to get delivery by ID: {}", deliveryId);

        DeliveryDTO delivery = deliveryService.findDeliveryById(deliveryId);

        if (delivery == null) {
            logger.info("No delivery found with ID: {}", deliveryId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery with ID: {}", deliveryId);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        logger.info("Received request to get all deliveries");

        List<DeliveryDTO> deliveries = deliveryService.findAllDeliveries();

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries", deliveries.size());
        return ResponseEntity.ok(deliveries);
    }

    @PostMapping
    public ResponseEntity<DeliveryDTO> createDelivery(@Validated @RequestBody DeliveryDTO deliveryDTO) {
        logger.info("Received request to create delivery for warehouse ID: {}", deliveryDTO.getWarehouse().getWarehouseId());

        if (deliveryDTO.getWarehouse().getWarehouseId() == null) {
            logger.error("Warehouse ID is null. Unable to create delivery.");
            return ResponseEntity.badRequest().build();
        }

        DeliveryDTO createdDelivery = deliveryService.createDelivery(deliveryDTO);

        if (createdDelivery == null) {
            logger.info("Delivery creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Delivery created successfully with ID: {}", createdDelivery.getDeliveryId());
        return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByWarehouse(@PathVariable UUID warehouseId) {
        logger.info("Received request to get deliveries by warehouse ID: {}", warehouseId);

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByWarehouse(warehouseId);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for warehouse ID: {}", warehouseId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for warehouse ID: {}", deliveries.size(), warehouseId);
        return ResponseEntity.ok(deliveries);
    }


    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryDTO> updateDelivery(
            @PathVariable UUID deliveryId,
            @Validated @RequestBody DeliveryDTO updatedDeliveryDTO) {
        logger.info("Received request to update delivery with ID: {}", deliveryId);

        DeliveryDTO updatedDelivery = deliveryService.updateDelivery(deliveryId, updatedDeliveryDTO);

        if (updatedDelivery == null) {
            logger.info("Delivery update failed. No delivery found with ID: {}", deliveryId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Delivery updated successfully with ID: {}", deliveryId);
        return ResponseEntity.ok(updatedDelivery);
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByProduct(@PathVariable UUID productId) {
        logger.info("Received request to get deliveries by product ID: {}", productId);

        if (productId == null) {
            logger.error("Invalid product ID provided.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByProduct(productId);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for product ID: {}", productId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for product ID: {}", deliveries.size(), productId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/delivery-agent/{agentId}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByDeliveryAgent(@PathVariable UUID agentId) {
        logger.info("Received request to get deliveries by delivery agent ID: {}", agentId);

        if (agentId == null) {
            logger.error("Invalid delivery agent ID provided.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByDeliveryAgent(agentId);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for delivery agent ID: {}", agentId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for delivery agent ID: {}", deliveries.size(), agentId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByStatus(@PathVariable DeliveryStatus status) {
        logger.info("Received request to get deliveries by status: {}", status);

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByStatus(status);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for status: {}", status);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for status: {}", deliveries.size(), status);
        return ResponseEntity.ok(deliveries);
    }



    @GetMapping("/date-range")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByWarehouseAndDateRange(
            @RequestParam UUID warehouseId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        logger.info("Received request to get deliveries by warehouse ID: {} and date range: {} to {}", warehouseId, startDate, endDate);

        if (warehouseId == null || startDate == null || endDate == null) {
            logger.error("Invalid parameters provided for fetching deliveries by warehouse and date range.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByWarehouseAndDateRange(warehouseId, startDate, endDate);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for warehouse ID: {} and date range: {} to {}", warehouseId, startDate, endDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for warehouse ID: {} and date range: {} to {}", deliveries.size(), warehouseId, startDate, endDate);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/status-and-date")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByStatusAndDateBefore(
            @RequestParam DeliveryStatus status,
            @RequestParam LocalDate date) {
        logger.info("Received request to get deliveries by status: {} and date before: {}", status, date);

        if (status == null || date == null) {
            logger.error("Invalid parameters provided for fetching deliveries by status and date.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByStatusAndDateBefore(status, date);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for status: {} and date before: {}", status, date);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for status: {} and date before: {}", deliveries.size(), status, date);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/product-and-damaged")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByProductAndIsDamaged(
            @RequestParam UUID productId,
            @RequestParam boolean isDamaged) {
        logger.info("Received request to get deliveries by product ID: {} and isDamaged: {}", productId, isDamaged);

        if (productId == null) {
            logger.error("Invalid parameters provided for fetching deliveries by product and damaged status.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByProductAndIsDamaged(productId, isDamaged);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for product ID: {} and isDamaged: {}", productId, isDamaged);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for product ID: {} and isDamaged: {}", deliveries.size(), productId, isDamaged);
        return ResponseEntity.ok(deliveries);
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByDate(@PathVariable LocalDate date) {
        logger.info("Received request to get deliveries by date: {}", date);

        if (date == null) {
            logger.error("Invalid date provided.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryDTO> deliveries = deliveryService.findDeliveriesByDate(date);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for date: {}", date);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} deliveries for date: {}", deliveries.size(), date);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/damaged")
    public ResponseEntity<List<DeliveryDTO>> getDamagedDeliveries() {
        logger.info("Received request to get damaged deliveries");

        List<DeliveryDTO> deliveries = deliveryService.findDamagedDeliveries();

        if (deliveries.isEmpty()) {
            logger.info("No damaged deliveries found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} damaged deliveries", deliveries.size());
        return ResponseEntity.ok(deliveries);
    }
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable UUID deliveryId) {
        logger.info("Received request to delete delivery with ID: {}", deliveryId);

        deliveryService.deleteDelivery(deliveryId);

        logger.info("Delivery deleted successfully with ID: {}", deliveryId);
        return ResponseEntity.noContent().build();
    }
}
