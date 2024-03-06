package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryScheduleDTO;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryScheduleService;
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
@RequestMapping("/api/delivery-schedules")
public class DeliveryScheduleController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryScheduleController.class);

    @Autowired
    private DeliveryScheduleService deliveryScheduleService;

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<DeliveryScheduleDTO>> getDeliverySchedulesByAgentId(@PathVariable UUID agentId) {
        logger.info("Received request to get delivery schedules by agent ID: {}", agentId);

        List<DeliveryScheduleDTO> deliverySchedules = deliveryScheduleService.findByAgentId(agentId);

        if (deliverySchedules == null) {
            logger.info("No delivery schedules found for agent ID: {}", agentId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery schedules for agent ID: {}", agentId);
        return ResponseEntity.ok(deliverySchedules);
    }

    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<List<DeliveryScheduleDTO>> getDeliverySchedulesByDeliveryId(@PathVariable UUID deliveryId) {
        logger.info("Received request to get delivery schedules by delivery ID: {}", deliveryId);

        List<DeliveryScheduleDTO> deliverySchedules = deliveryScheduleService.findByDeliveryId(deliveryId);

        if (deliverySchedules == null) {
            logger.info("No delivery schedules found for delivery ID: {}", deliveryId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery schedules for delivery ID: {}", deliveryId);
        return ResponseEntity.ok(deliverySchedules);
    }

    @GetMapping("/completed/{isCompleted}")
    public ResponseEntity<List<DeliveryScheduleDTO>> getDeliverySchedulesByCompletionStatus(@PathVariable boolean isCompleted) {
        logger.info("Received request to get delivery schedules by completion status: {}", isCompleted);

        List<DeliveryScheduleDTO> deliverySchedules = deliveryScheduleService.findByIsCompleted(isCompleted);

        if (deliverySchedules == null) {
            logger.info("No delivery schedules found with completion status: {}", isCompleted);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery schedules with completion status: {}", isCompleted);
        return ResponseEntity.ok(deliverySchedules);
    }

    @GetMapping("/rescheduled/{isRescheduled}")
    public ResponseEntity<List<DeliveryScheduleDTO>> getDeliverySchedulesByRescheduleStatus(@PathVariable boolean isRescheduled) {
        logger.info("Received request to get delivery schedules by reschedule status: {}", isRescheduled);

        List<DeliveryScheduleDTO> deliverySchedules = deliveryScheduleService.findByIsRescheduled(isRescheduled);

        if (deliverySchedules == null) {
            logger.info("No delivery schedules found with reschedule status: {}", isRescheduled);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery schedules with reschedule status: {}", isRescheduled);
        return ResponseEntity.ok(deliverySchedules);
    }
}
