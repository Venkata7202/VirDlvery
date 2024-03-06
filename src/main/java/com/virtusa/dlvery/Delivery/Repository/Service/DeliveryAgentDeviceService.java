package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryAgentDeviceDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliveryAgent;
import com.virtusa.dlvery.Delivery.Entities.DeliveryAgentDevice;
import com.virtusa.dlvery.Delivery.Repository.DeliveryAgentDeviceRepository;
import com.virtusa.dlvery.Delivery.Repository.DeliveryAgentRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryAgentDeviceService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryAgentDeviceService.class);

    @Autowired
    private DeliveryAgentDeviceRepository deliveryAgentDeviceRepository;
    private DeliveryAgentRepository deliveryAgentRepository;


    public DeliveryAgentDeviceDTO findDeliveryAgentDeviceById(UUID deviceId) {
        logger.info("Fetching delivery agent device by ID: {}", deviceId);

        if (deviceId == null) {
            logger.error("Delivery agent device ID is null. Unable to fetch delivery agent device.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        DeliveryAgentDevice deliveryAgentDevice = deliveryAgentDeviceRepository.findDeliveryAgentDeviceByDeviceId(deviceId);

        if (deliveryAgentDevice == null) {
            logger.info("No delivery agent device found with ID: {}", deviceId);
            // Handle case when no delivery agent device is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToDeliveryAgentDeviceDTO(deliveryAgentDevice);
    }

    public List<DeliveryAgentDeviceDTO> findAllDeliveryAgentDevices() {
        logger.info("Fetching all delivery agent devices");

        List<DeliveryAgentDevice> deliveryAgentDevices = deliveryAgentDeviceRepository.findAll();

        if (deliveryAgentDevices.isEmpty()) {
            logger.info("No delivery agent devices found");
            // Handle case when no delivery agent devices are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryAgentDeviceDTOList(deliveryAgentDevices);
    }

    public DeliveryAgentDeviceDTO createDeliveryAgentDevice(DeliveryAgentDeviceDTO deviceDTO) {
        logger.info("Creating delivery agent device: {}", deviceDTO.getDeviceName());

        if (deviceDTO.getDeviceName() == null) {
            logger.error("Delivery agent device name is null. Unable to create delivery agent device.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        DeliveryAgentDevice deliveryAgentDevice = DTOConversionUtil.convertToDeliveryAgentDevice(deviceDTO);

        // Save delivery agent device
        DeliveryAgentDevice savedDeliveryAgentDevice = deliveryAgentDeviceRepository.save(deliveryAgentDevice);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToDeliveryAgentDeviceDTO(savedDeliveryAgentDevice);
    }


    public List<DeliveryAgentDeviceDTO> findDeliveryAgentDevicesByAgent(UUID agentId) {
        logger.info("Fetching delivery agent devices by agent ID: {}", agentId);

        DeliveryAgent deliveryAgent = deliveryAgentRepository.findDeliveryAgentByAgentId(agentId);



        if (deliveryAgent == null) {
            logger.error("Delivery agent null. Unable to find delivery agent.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        List<DeliveryAgentDevice> deliveryAgentDevices = deliveryAgentDeviceRepository.findDeliveryAgentDevicesByAgent(deliveryAgent);

        if (deliveryAgentDevices.isEmpty()) {
            logger.info("No delivery agent devices found for agent ID: {}", agentId);
            // Handle case when no delivery agent devices are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryAgentDeviceDTOList(deliveryAgentDevices);
    }


    public DeliveryAgentDeviceDTO updateDeliveryAgentDevice(UUID deviceId, DeliveryAgentDeviceDTO updatedDeviceDTO) {
        logger.info("Updating delivery agent device with ID: {}", deviceId);

        // Fetch existing device
        DeliveryAgentDevice existingDevice = deliveryAgentDeviceRepository.findDeliveryAgentDeviceByDeviceId(deviceId);

        if (existingDevice == null) {
            logger.error("No delivery agent device found with ID: {}", deviceId);
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        existingDevice.setDeviceName(updatedDeviceDTO.getDeviceName());
        existingDevice.setDeviceType(updatedDeviceDTO.getDeviceType());

        // Save the updated device
        DeliveryAgentDevice updatedDevice = deliveryAgentDeviceRepository.save(existingDevice);

        // Convert updated entity back to DTO
        return DTOConversionUtil.convertToDeliveryAgentDeviceDTO(updatedDevice);
    }

    public void deleteDeliveryAgentDevice(UUID deviceId) {
        logger.info("Deleting delivery agent device with ID: {}", deviceId);
        deliveryAgentDeviceRepository.deleteById(deviceId);
    }
}
