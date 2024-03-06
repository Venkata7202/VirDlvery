package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryDTO;
import com.virtusa.dlvery.Delivery.Entities.Delivery;
import com.virtusa.dlvery.Delivery.Enum.DeliveryStatus;
import com.virtusa.dlvery.Delivery.Repository.DeliveryRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

    @Autowired
    private DeliveryRepository deliveryRepository;

    public DeliveryDTO findDeliveryById(UUID deliveryId) {
        logger.info("Fetching delivery by ID: {}", deliveryId);

        if (deliveryId == null) {
            logger.error("Delivery ID is null. Unable to fetch delivery.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId);

        if (delivery == null) {
            logger.info("No delivery found with ID: {}", deliveryId);
            // Handle case when no delivery is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToDeliveryDTO(delivery);
    }

    public List<DeliveryDTO> findAllDeliveries() {
        logger.info("Fetching all deliveries");

        List<Delivery> deliveries = deliveryRepository.findAll();

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found");
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        logger.info("Creating delivery for warehouse ID: {}", deliveryDTO.getWarehouse().getWarehouseId());

        if (deliveryDTO.getWarehouse().getWarehouseId() == null) {
            logger.error("Warehouse ID is null. Unable to create delivery.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Delivery delivery = DTOConversionUtil.convertToDelivery(deliveryDTO);

        // Save delivery
        Delivery savedDelivery = deliveryRepository.save(delivery);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToDeliveryDTO(savedDelivery);
    }

    public List<DeliveryDTO> findDeliveriesByWarehouse(UUID warehouseId) {
        logger.info("Fetching deliveries by warehouse ID: {}", warehouseId);

        List<Delivery> deliveries = deliveryRepository.findByWarehouse_WarehouseId(warehouseId);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for warehouse ID: {}", warehouseId);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByProduct(UUID productId) {
        logger.info("Fetching deliveries by product ID: {}", productId);

        List<Delivery> deliveries = deliveryRepository.findByProduct_ProductId(productId);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for product ID: {}", productId);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByDeliveryAgent(UUID agentId) {
        logger.info("Fetching deliveries by delivery agent ID: {}", agentId);

        List<Delivery> deliveries = deliveryRepository.findByDeliveryAgent_AgentId(agentId);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for delivery agent ID: {}", agentId);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByStatus(DeliveryStatus status) {
        logger.info("Fetching deliveries by status: {}", status);

        List<Delivery> deliveries = deliveryRepository.findByStatus(status);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for status: {}", status);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByDate(LocalDate date) {
        logger.info("Fetching deliveries by date: {}", date);

        List<Delivery> deliveries = deliveryRepository.findByDate(date);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for date: {}", date);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDamagedDeliveries() {
        logger.info("Fetching damaged deliveries");

        List<Delivery> deliveries = deliveryRepository.findByIsDamaged(true);

        if (deliveries.isEmpty()) {
            logger.info("No damaged deliveries found");
            // Handle case when no damaged deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByWarehouseAndDateRange(UUID warehouseId, LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching deliveries by warehouse ID: {} and date range: {} to {}", warehouseId, startDate, endDate);

        List<Delivery> deliveries = deliveryRepository.findByWarehouse_WarehouseIdAndDateBetween(warehouseId, startDate, endDate);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for warehouse ID: {} and date range: {} to {}", warehouseId, startDate, endDate);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByStatusAndDateBefore(DeliveryStatus status, LocalDate date) {
        logger.info("Fetching deliveries by status: {} and date before: {}", status, date);

        List<Delivery> deliveries = deliveryRepository.findByStatusAndDateBefore(status, date);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for status: {} and date before: {}", status, date);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public List<DeliveryDTO> findDeliveriesByProductAndIsDamaged(UUID productId, boolean isDamaged) {
        logger.info("Fetching deliveries by product ID: {} and isDamaged: {}", productId, isDamaged);

        List<Delivery> deliveries = deliveryRepository.findByProduct_ProductIdAndIsDamaged(productId, isDamaged);

        if (deliveries.isEmpty()) {
            logger.info("No deliveries found for product ID: {} and isDamaged: {}", productId, isDamaged);
            // Handle case when no deliveries are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToDeliveryDTOList(deliveries);
    }

    public void deleteDelivery(UUID deliveryId) {
        logger.info("Deleting delivery with ID: {}", deliveryId);
        deliveryRepository.deleteByDeliveryId(deliveryId);
    }

    public DeliveryDTO updateDelivery(UUID deliveryId, DeliveryDTO updatedDeliveryDTO) {
        logger.info("Updating delivery with ID: {}", deliveryId);

        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);

        if (optionalDelivery.isEmpty()) {
            logger.info("No delivery found with ID: {}", deliveryId);
            return null;
        }

        Delivery delivery = optionalDelivery.get();



        // Save the updated delivery
        Delivery updatedDelivery = deliveryRepository.save(delivery);

        logger.info("Delivery updated successfully with ID: {}", deliveryId);
        return DTOConversionUtil.convertToDeliveryDTO(updatedDelivery);
    }

}





