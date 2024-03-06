package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryPriorityDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliveryPriority;
import com.virtusa.dlvery.Delivery.Repository.DeliveryPriorityRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryPriorityService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryPriorityService.class);

    @Autowired
    private DeliveryPriorityRepository deliveryPriorityRepository;

    public DeliveryPriorityDTO findByPriorityName(String priorityName) {
        logger.info("Finding delivery priority by name: {}", priorityName);

        if (priorityName == null) {
            logger.error("Priority name is null. Unable to find delivery priority.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        DeliveryPriority deliveryPriority = deliveryPriorityRepository.findByPriorityName(priorityName);

        if (deliveryPriority == null) {
            logger.info("No delivery priority found with name: {}", priorityName);
            // Handle case when no delivery priority is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToDeliveryPriorityDTO(deliveryPriority);

    }

    public List<DeliveryPriorityDTO> getAllDeliveryPriorities() {
        logger.info("Fetching all delivery priorities");
        List<DeliveryPriority> deliveryPriorities = deliveryPriorityRepository.findAll();
        return DTOConversionUtil.convertToDeliveryPriorityDTOList(deliveryPriorities);
    }

    public DeliveryPriorityDTO createDeliveryPriority(DeliveryPriorityDTO deliveryPriorityDTO) {
        logger.info("Creating delivery priority: {}", deliveryPriorityDTO.getPriorityName());

        if (deliveryPriorityDTO.getPriorityName() == null) {
            logger.error("Priority name is null. Unable to create delivery priority.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Check if the delivery priority with the same name already exists
        DeliveryPriority existingDeliveryPriority = deliveryPriorityRepository.findByPriorityName(deliveryPriorityDTO.getPriorityName());

        if (existingDeliveryPriority != null) {
            logger.info("Delivery priority with name '{}' already exists. Unable to create duplicate delivery priority.", deliveryPriorityDTO.getPriorityName());
            // Handle case when the delivery priority already exists, return null/empty DTO
            return null;
        }
        DeliveryPriority deliveryPriority = DTOConversionUtil.convertToDeliveryPriority(deliveryPriorityDTO);
        DeliveryPriority savedDeliveryPriority = deliveryPriorityRepository.save(deliveryPriority);

        return DTOConversionUtil.convertToDeliveryPriorityDTO(savedDeliveryPriority);
    }

    public DeliveryPriorityDTO updateDeliveryPriority(UUID priorityId, DeliveryPriorityDTO updatedDeliveryPriorityDTO) {
        logger.info("Updating delivery priority with ID: {}", priorityId);

        // Check if delivery priority with given ID exists
        if (!deliveryPriorityRepository.existsById(priorityId)) {
            logger.info("No delivery priority found with ID: {}", priorityId);
            // Handle case when no delivery priority is found, return null/empty DTO
            return null;
        }

        // Set the ID for the existing delivery priority
        updatedDeliveryPriorityDTO.setPriorityId(priorityId);

        DeliveryPriority deliveryPriority = DTOConversionUtil.convertToDeliveryPriority(updatedDeliveryPriorityDTO);
        DeliveryPriority savedDeliveryPriority = deliveryPriorityRepository.save(deliveryPriority);

        return  DTOConversionUtil.convertToDeliveryPriorityDTO(savedDeliveryPriority);
    }

    public void deleteDeliveryPriority(UUID priorityId) {
        logger.info("Deleting delivery priority with ID: {}", priorityId);
        deliveryPriorityRepository.deleteById(priorityId);
    }
}
