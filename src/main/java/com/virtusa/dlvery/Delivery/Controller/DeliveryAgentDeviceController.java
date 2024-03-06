package com.virtusa.dlvery.Delivery.Controller;

import com.virtusa.dlvery.Delivery.DTO.DeliveryAgentDeviceDTO;
import com.virtusa.dlvery.Delivery.Repository.Service.DeliveryAgentDeviceService;
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
@RequestMapping("/api/delivery-agent-devices")
public class DeliveryAgentDeviceController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryAgentDeviceController.class);

    @Autowired
    private DeliveryAgentDeviceService deliveryAgentDeviceService;

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeliveryAgentDeviceDTO> getDeliveryAgentDeviceById(@PathVariable UUID deviceId) {
        logger.info("Received request to get delivery agent device by ID: {}", deviceId);

        DeliveryAgentDeviceDTO deviceDTO = deliveryAgentDeviceService.findDeliveryAgentDeviceById(deviceId);

        if (deviceDTO == null) {
            logger.info("No delivery agent device found with ID: {}", deviceId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning delivery agent device: {}", deviceDTO.getDeviceName());
        return ResponseEntity.ok(deviceDTO);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryAgentDeviceDTO>> getAllDeliveryAgentDevices() {
        logger.info("Received request to get all delivery agent devices");

        List<DeliveryAgentDeviceDTO> deviceDTOs = deliveryAgentDeviceService.findAllDeliveryAgentDevices();

        if (deviceDTOs.isEmpty()) {
            logger.info("No delivery agent devices found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery agent devices", deviceDTOs.size());
        return ResponseEntity.ok(deviceDTOs);
    }

    @PostMapping
    public ResponseEntity<DeliveryAgentDeviceDTO> createDeliveryAgentDevice(@Validated @RequestBody DeliveryAgentDeviceDTO deviceDTO) {
        logger.info("Received request to create delivery agent device: {}", deviceDTO.getDeviceName());

        DeliveryAgentDeviceDTO createdDeviceDTO = deliveryAgentDeviceService.createDeliveryAgentDevice(deviceDTO);

        if (createdDeviceDTO == null) {
            logger.info("Delivery agent device creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Delivery agent device created successfully: {}", createdDeviceDTO.getDeviceName());
        return ResponseEntity.ok(createdDeviceDTO);
    }

    @GetMapping("/byAgent/{agentId}")
    public ResponseEntity<List<DeliveryAgentDeviceDTO>> getDeliveryAgentDevicesByAgent(@PathVariable UUID agentId) {
        logger.info("Received request to get delivery agent devices by agent ID: {}", agentId);

        List<DeliveryAgentDeviceDTO> deviceDTOs = deliveryAgentDeviceService.findDeliveryAgentDevicesByAgent(agentId);

        if (deviceDTOs.isEmpty()) {
            logger.info("No delivery agent devices found for agent ID: {}", agentId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} delivery agent devices for agent ID: {}", deviceDTOs.size(), agentId);
        return ResponseEntity.ok(deviceDTOs);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDeliveryAgentDevice(@PathVariable UUID deviceId) {
        logger.info("Deleting delivery agent device with ID: {}", deviceId);

        deliveryAgentDeviceService.deleteDeliveryAgentDevice(deviceId);

        logger.info("Delivery agent device deleted successfully with ID: {}", deviceId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/update/{deviceId}")
    public ResponseEntity<DeliveryAgentDeviceDTO> updateDeliveryAgentDevice(
            @PathVariable UUID deviceId,
            @RequestBody DeliveryAgentDeviceDTO updatedDeviceDTO) {
        logger.info("Received request to update delivery agent device with ID: {}", deviceId);

        // Call the service to update the delivery agent device
        DeliveryAgentDeviceDTO updatedDevice = deliveryAgentDeviceService.updateDeliveryAgentDevice(deviceId, updatedDeviceDTO);

        if (updatedDevice == null) {
            logger.info("Delivery agent device update failed. No device found with ID: {}", deviceId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Delivery agent device updated successfully with ID: {}", deviceId);
        return ResponseEntity.ok(updatedDevice);
    }



}
