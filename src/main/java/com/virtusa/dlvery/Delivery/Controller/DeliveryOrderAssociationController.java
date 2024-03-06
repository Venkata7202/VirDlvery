package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryOrderAssociationDTO;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryOrderAssociationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-order-associations")
public class DeliveryOrderAssociationController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryOrderAssociationController.class);

    @Autowired
    private DeliveryOrderAssociationService deliveryOrderAssociationService;

    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<List<DeliveryOrderAssociationDTO>> getDeliveryOrderAssociationsByDeliveryId(@PathVariable UUID deliveryId) {
        logger.info("Received request to get delivery order associations by delivery ID: {}", deliveryId);

        List<DeliveryOrderAssociationDTO> deliveryOrderAssociations = deliveryOrderAssociationService.findByDeliveryId(deliveryId);

        if (deliveryOrderAssociations == null) {
            logger.info("No delivery order associations found for delivery ID: {}", deliveryId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery order associations for delivery ID: {}", deliveryId);
        return ResponseEntity.ok(deliveryOrderAssociations);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<DeliveryOrderAssociationDTO>> getDeliveryOrderAssociationsByOrderId(@PathVariable UUID orderId) {
        logger.info("Received request to get delivery order associations by order ID: {}", orderId);

        List<DeliveryOrderAssociationDTO> deliveryOrderAssociations = deliveryOrderAssociationService.findByOrderId(orderId);

        if (deliveryOrderAssociations == null) {
            logger.info("No delivery order associations found for order ID: {}", orderId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery order associations for order ID: {}", orderId);
        return ResponseEntity.ok(deliveryOrderAssociations);
    }

    @PostMapping
    public ResponseEntity<DeliveryOrderAssociationDTO> saveDeliveryOrderAssociation(@RequestBody DeliveryOrderAssociationDTO deliveryOrderAssociationDTO) {
        logger.info("Received request to save delivery order association: {}", deliveryOrderAssociationDTO);

        DeliveryOrderAssociationDTO savedDeliveryOrderAssociation = deliveryOrderAssociationService.saveDeliveryOrderAssociation(deliveryOrderAssociationDTO);

        if (savedDeliveryOrderAssociation == null) {
            logger.info("Saving delivery order association failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Delivery order association saved successfully: {}", savedDeliveryOrderAssociation.getDeliveryId());
        return ResponseEntity.ok(savedDeliveryOrderAssociation);
    }

    @DeleteMapping("/delivery/{deliveryId}/order/{orderId}")
    public ResponseEntity<Void> deleteDeliveryOrderAssociation(@PathVariable UUID deliveryId, @PathVariable UUID orderId) {
        logger.info("Deleting delivery order association with Delivery ID: {} and Order ID: {}", deliveryId, orderId);

        deliveryOrderAssociationService.deleteDeliveryOrderAssociation(deliveryId, orderId);

        logger.info("Delivery order association deleted successfully with Delivery ID: {} and Order ID: {}", deliveryId, orderId);
        return ResponseEntity.noContent().build();
    }
}
