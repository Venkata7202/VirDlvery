package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryPriorityDTO;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryPriorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-priorities")
public class DeliveryPriorityController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryPriorityController.class);

    @Autowired
    private DeliveryPriorityService deliveryPriorityService;

    @GetMapping("/{priorityName}")
    public ResponseEntity<DeliveryPriorityDTO> getDeliveryPriorityByPriorityName(@PathVariable String priorityName) {
        logger.info("Received request to get delivery priority by name: {}", priorityName);

        DeliveryPriorityDTO deliveryPriority = deliveryPriorityService.findByPriorityName(priorityName);

        if (deliveryPriority == null) {
            logger.info("No delivery priority found with name: {}", priorityName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery priority: {}", deliveryPriority.getPriorityName());
        return ResponseEntity.ok(deliveryPriority);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryPriorityDTO>> getAllDeliveryPriorities() {
        logger.info("Received request to get all delivery priorities");

        List<DeliveryPriorityDTO> deliveryPriorities = deliveryPriorityService.getAllDeliveryPriorities();

        if (deliveryPriorities.isEmpty()) {
            logger.info("No delivery priorities found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery priorities", deliveryPriorities.size());
        return ResponseEntity.ok(deliveryPriorities);
    }

    @PostMapping
    public ResponseEntity<DeliveryPriorityDTO> createDeliveryPriority(@RequestBody DeliveryPriorityDTO deliveryPriorityDTO) {
        logger.info("Received request to create delivery priority: {}", deliveryPriorityDTO.getPriorityName());

        DeliveryPriorityDTO createdDeliveryPriority = deliveryPriorityService.createDeliveryPriority(deliveryPriorityDTO);

        if (createdDeliveryPriority == null) {
            logger.info("Delivery priority creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Delivery priority created successfully: {}", createdDeliveryPriority.getPriorityName());
        return ResponseEntity.ok(createdDeliveryPriority);
    }

    @PutMapping("/{priorityId}")
    public ResponseEntity<DeliveryPriorityDTO> updateDeliveryPriority(
            @PathVariable UUID priorityId,
            @RequestBody DeliveryPriorityDTO updatedDeliveryPriorityDTO) {
        logger.info("Received request to update delivery priority with ID: {}", priorityId);

        DeliveryPriorityDTO updatedDeliveryPriority = deliveryPriorityService.updateDeliveryPriority(priorityId, updatedDeliveryPriorityDTO);

        if (updatedDeliveryPriority == null) {
            logger.info("Delivery priority update failed. No delivery priority found with ID: {}", priorityId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Delivery priority updated successfully: {}", updatedDeliveryPriority.getPriorityName());
        return ResponseEntity.ok(updatedDeliveryPriority);
    }

    @DeleteMapping("/{priorityId}")
    public ResponseEntity<Void> deleteDeliveryPriority(@PathVariable UUID priorityId) {
        logger.info("Deleting delivery priority with ID: {}", priorityId);

        deliveryPriorityService.deleteDeliveryPriority(priorityId);

        logger.info("Delivery priority deleted successfully with ID: {}", priorityId);
        return ResponseEntity.noContent().build();
    }
}
