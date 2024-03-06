package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryAgentDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliveryAgent;
import com.virtusa.dlvery.Delivery.Repository.DeliveryAgentRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryAgentService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryAgentService.class);

    @Autowired
    private DeliveryAgentRepository deliveryAgentRepository;

    public DeliveryAgentDTO findDeliveryAgentById(UUID agentId) {
        logger.info("Fetching delivery agent by ID: {}", agentId);

        if (agentId == null) {
            logger.error("Delivery agent ID is null. Unable to fetch delivery agent.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        DeliveryAgent deliveryAgent = deliveryAgentRepository.findDeliveryAgentByAgentId(agentId);

        if (deliveryAgent == null) {
            logger.info("No delivery agent found with ID: {}", agentId);
            // Handle case when no delivery agent is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToDeliveryAgentDTO(deliveryAgent);
    }

    public List<DeliveryAgentDTO> findAllDeliveryAgents() {
        logger.info("Fetching all delivery agents");

        List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findAll();

        if (deliveryAgents.isEmpty()) {
            logger.info("No delivery agents found");
            // Handle case when no delivery agents are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryAgentDTOList(deliveryAgents);
    }

    public DeliveryAgentDTO createDeliveryAgent(DeliveryAgentDTO agentDTO) {
        logger.info("Creating delivery agent: {}", agentDTO.getAgentName());

        if (agentDTO.getAgentName() == null) {
            logger.error("Delivery agent name is null. Unable to create delivery agent.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Check if the delivery agent with the same name already exists
        DeliveryAgent existingAgent = deliveryAgentRepository.findByAgentName(agentDTO.getAgentName());

        if (existingAgent != null) {
            logger.info("Delivery agent with name '{}' already exists. Unable to create duplicate delivery agent.", agentDTO.getAgentName());
            // Handle case when the delivery agent already exists, return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        DeliveryAgent deliveryAgent = DTOConversionUtil.convertToDeliveryAgent(agentDTO);

        // Save delivery agent
        DeliveryAgent savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToDeliveryAgentDTO(savedDeliveryAgent);
    }

    public List<DeliveryAgentDTO> findDeliveryAgentsByName(String agentName) {
        logger.info("Fetching delivery agents by name containing: {}", agentName);

        List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findByAgentNameContaining(agentName);

        if (deliveryAgents.isEmpty()) {
            logger.info("No delivery agents found with name containing: {}", agentName);
            // Handle case when no delivery agents are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryAgentDTOList(deliveryAgents);
    }

    public void deleteDeliveryAgent(UUID agentId) {
        logger.info("Deleting delivery agent with ID: {}", agentId);
        deliveryAgentRepository.deleteByAgentId(agentId);
    }



    public DeliveryAgentDTO updateDeliveryAgent(UUID agentId, DeliveryAgentDTO updatedAgentDTO) {
        logger.info("Updating delivery agent with ID: {}", agentId);

        // Fetch existing agent
        DeliveryAgent existingAgent = deliveryAgentRepository.findDeliveryAgentByAgentId(agentId);

        if (existingAgent == null) {
            logger.error("No delivery agent found with ID: {}", agentId);
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Update fields based on the provided DTO
        existingAgent.setAgentName(updatedAgentDTO.getAgentName());
        // Update other fields as needed...

        // Save the updated agent
        DeliveryAgent updatedAgent = deliveryAgentRepository.save(existingAgent);

        // Convert updated entity back to DTO
        return DTOConversionUtil.convertToDeliveryAgentDTO(updatedAgent);
    }
}

