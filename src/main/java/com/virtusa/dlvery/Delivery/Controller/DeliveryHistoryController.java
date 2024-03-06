package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryHistoryDTO;
import com.virtusa.dlvery.Delivery.Enum.EventType;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-history")
public class DeliveryHistoryController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryHistoryController.class);

    @Autowired
    private DeliveryHistoryService deliveryHistoryService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<DeliveryHistoryDTO>> getDeliveryHistoryByScheduleId(@PathVariable UUID scheduleId) {
        logger.info("Received request to get delivery history by schedule ID: {}", scheduleId);

        List<DeliveryHistoryDTO> deliveryHistory = deliveryHistoryService.findByScheduleId(scheduleId);

        if (deliveryHistory == null) {
            logger.info("No delivery history found with schedule ID: {}", scheduleId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery history for schedule ID: {}", scheduleId);
        return ResponseEntity.ok(deliveryHistory);
    }

    @GetMapping("/event-type/{eventType}")
    public ResponseEntity<List<DeliveryHistoryDTO>> getDeliveryHistoryByEventType(@PathVariable String eventType) {
        logger.info("Received request to get delivery history by event type: {}", eventType);

        List<DeliveryHistoryDTO> deliveryHistory = deliveryHistoryService.findByEventType(EventType.valueOf(eventType));

        if (deliveryHistory == null) {
            logger.info("No delivery history found with event type: {}", eventType);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery history for event type: {}", eventType);
        return ResponseEntity.ok(deliveryHistory);
    }

    @GetMapping("/agent/{agentName}")
    public ResponseEntity<List<DeliveryHistoryDTO>> getDeliveryHistoryByAgentName(@PathVariable String agentName) {
        logger.info("Received request to get delivery history by agent name: {}", agentName);

        List<DeliveryHistoryDTO> deliveryHistory = deliveryHistoryService.findByDeliveryAgentName(agentName);

        if (deliveryHistory == null) {
            logger.info("No delivery history found with agent name: {}", agentName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery history for agent name: {}", agentName);
        return ResponseEntity.ok(deliveryHistory);
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<DeliveryHistoryDTO>> getDeliveryHistoryByCustomerName(@PathVariable String customerName) {
        logger.info("Received request to get delivery history by customer name: {}", customerName);

        List<DeliveryHistoryDTO> deliveryHistory = deliveryHistoryService.findByCustomerName(customerName);

        if (deliveryHistory == null) {
            logger.info("No delivery history found with customer name: {}", customerName);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery history for customer name: {}", customerName);
        return ResponseEntity.ok(deliveryHistory);
    }

    @GetMapping("/timestamp/{eventTimestamp}")
    public ResponseEntity<List<DeliveryHistoryDTO>> getDeliveryHistoryBeforeTimestamp(@PathVariable String eventTimestamp) {
        logger.info("Received request to get delivery history before timestamp: {}", eventTimestamp);

        List<DeliveryHistoryDTO> deliveryHistory = deliveryHistoryService.findEventsBeforeTimestamp(LocalDateTime.parse(eventTimestamp));

        if (deliveryHistory == null) {
            logger.info("No delivery history found before timestamp: {}", eventTimestamp);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery history before timestamp: {}", eventTimestamp);
        return ResponseEntity.ok(deliveryHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryHistoryDTO> getDeliveryHistoryById(@PathVariable UUID id) {
        logger.info("Received request to get delivery history by ID: {}", id);

        Optional<DeliveryHistoryDTO> deliveryHistory = deliveryHistoryService.findById(id);

        if (deliveryHistory.isEmpty()) {
            logger.info("No delivery history found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery history for ID: {}", id);
        return ResponseEntity.ok(deliveryHistory.get());
    }

    @PostMapping
    public ResponseEntity<DeliveryHistoryDTO> saveDeliveryHistory(@RequestBody DeliveryHistoryDTO deliveryHistoryDTO) {
        logger.info("Received request to save delivery history: {}", deliveryHistoryDTO);

        DeliveryHistoryDTO savedDeliveryHistory = deliveryHistoryService.saveDeliveryHistory(deliveryHistoryDTO);

        if (savedDeliveryHistory == null) {
            logger.info("Saving delivery history failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Delivery history saved successfully: {}", savedDeliveryHistory.getHistoryId());
        return ResponseEntity.ok(savedDeliveryHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryHistory(@PathVariable UUID id) {
        logger.info("Deleting delivery history by ID: {}", id);

        deliveryHistoryService.deleteById(id);

        logger.info("Delivery history deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
