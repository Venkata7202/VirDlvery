package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryAgentDTO;
import com.virtusa.dlvery.Delivery.DTO.DeliveryAgentDeviceDTO;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryAgentDeviceService;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryAgentService;
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
@RequestMapping("/api/delivery-agents")
public class DeliveryAgentController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryAgentController.class);

    @Autowired
    private DeliveryAgentService deliveryAgentService;

    @Autowired
    private DeliveryAgentDeviceService deliveryAgentDeviceService;

    @GetMapping("/{agentId}")
    public ResponseEntity<DeliveryAgentDTO> getDeliveryAgentById(@PathVariable UUID agentId) {
        logger.info("Received request to get delivery agent by ID: {}", agentId);

        DeliveryAgentDTO deliveryAgent = deliveryAgentService.findDeliveryAgentById(agentId);

        if (deliveryAgent == null) {
            logger.info("No delivery agent found with ID: {}", agentId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery agent: {}", deliveryAgent.getAgentName());
        return ResponseEntity.ok(deliveryAgent);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryAgentDTO>> getAllDeliveryAgents() {
        logger.info("Received request to get all delivery agents");

        List<DeliveryAgentDTO> deliveryAgents = deliveryAgentService.findAllDeliveryAgents();

        if (deliveryAgents.isEmpty()) {
            logger.info("No delivery agents found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery agents", deliveryAgents.size());
        return ResponseEntity.ok(deliveryAgents);
    }

    @PostMapping
    public ResponseEntity<DeliveryAgentDTO> createDeliveryAgent(@Validated @RequestBody DeliveryAgentDTO agentDTO) {
        logger.info("Received request to create delivery agent: {}", agentDTO.getAgentName());

        DeliveryAgentDTO createdDeliveryAgent = deliveryAgentService.createDeliveryAgent(agentDTO);

        if (createdDeliveryAgent == null) {
            logger.info("Delivery agent creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Delivery agent created successfully: {}", createdDeliveryAgent.getAgentName());
        return new ResponseEntity<>(createdDeliveryAgent, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeliveryAgentDTO>> searchDeliveryAgentsByName(@RequestParam String agentName) {
        logger.info("Received request to search delivery agents by name containing: {}", agentName);

        List<DeliveryAgentDTO> deliveryAgents = deliveryAgentService.findDeliveryAgentsByName(agentName);

        if (deliveryAgents.isEmpty()) {
            logger.info("No delivery agents found with name containing: {}", agentName);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery agents", deliveryAgents.size());
        return ResponseEntity.ok(deliveryAgents);
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<Void> deleteDeliveryAgent(@PathVariable UUID agentId) {
        logger.info("Deleting delivery agent with ID: {}", agentId);

        deliveryAgentService.deleteDeliveryAgent(agentId);

        logger.info("Delivery agent deleted successfully with ID: {}", agentId);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/update/{agentId}")
    public ResponseEntity<DeliveryAgentDTO> updateDeliveryAgent(
            @PathVariable UUID agentId,
            @RequestBody DeliveryAgentDTO updatedAgentDTO) {
        logger.info("Received request to update delivery agent with ID: {}", agentId);

        // Call the service to update the delivery agent
        DeliveryAgentDTO updatedAgent = deliveryAgentService.updateDeliveryAgent(agentId, updatedAgentDTO);

        if (updatedAgent == null) {
            logger.info("Delivery agent update failed. No agent found with ID: {}", agentId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Delivery agent updated successfully with ID: {}", agentId);
        return ResponseEntity.ok(updatedAgent);
    }


}
