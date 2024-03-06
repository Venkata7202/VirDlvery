package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryTrackingDTO;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-tracking")
public class DeliveryTrackingController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryTrackingController.class);

    @Autowired
    private DeliveryTrackingService deliveryTrackingService;

    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<List<DeliveryTrackingDTO>> getDeliveryTrackingByDeliveryId(@PathVariable UUID deliveryId) {
        logger.info("Received request to get delivery tracking records by delivery ID: {}", deliveryId);

        if (deliveryId == null) {
            logger.error("Invalid delivery ID provided.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryTrackingDTO> deliveryTrackingList = deliveryTrackingService.findByDeliveryId(deliveryId);

        if (deliveryTrackingList.isEmpty()) {
            logger.info("No delivery tracking records found for delivery ID: {}", deliveryId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery tracking records for delivery ID: {}", deliveryTrackingList.size(), deliveryId);
        return ResponseEntity.ok(deliveryTrackingList);
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<DeliveryTrackingDTO>> getDeliveryTrackingByCustomerName(@PathVariable String customerName) {
        logger.info("Received request to get delivery tracking records by customer name: {}", customerName);

        if (customerName == null || customerName.isEmpty()) {
            logger.error("Invalid customer name provided.");
            return ResponseEntity.badRequest().build();
        }

        List<DeliveryTrackingDTO> deliveryTrackingList = deliveryTrackingService.findByCustomerName(customerName);

        if (deliveryTrackingList.isEmpty()) {
            logger.info("No delivery tracking records found for customer: {}", customerName);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery tracking records for customer: {}", deliveryTrackingList.size(), customerName);
        return ResponseEntity.ok(deliveryTrackingList);
    }

    @GetMapping("/door-lock/{doorLockStatus}")
    public ResponseEntity<List<DeliveryTrackingDTO>> getDeliveryTrackingByDoorLockStatus(@PathVariable boolean doorLockStatus) {
        logger.info("Received request to get delivery tracking records by door lock status: {}", doorLockStatus);

        List<DeliveryTrackingDTO> deliveryTrackingList = deliveryTrackingService.findByDoorLockStatus(doorLockStatus);

        if (deliveryTrackingList.isEmpty()) {
            logger.info("No delivery tracking records found for door lock status: {}", doorLockStatus);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery tracking records for door lock status: {}", deliveryTrackingList.size(), doorLockStatus);
        return ResponseEntity.ok(deliveryTrackingList);
    }
}
