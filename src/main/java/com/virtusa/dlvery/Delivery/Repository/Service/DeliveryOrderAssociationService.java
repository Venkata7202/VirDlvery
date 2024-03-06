package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryOrderAssociationDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliveryOrderAssociation;
import com.virtusa.dlvery.Delivery.Repository.DeliveryOrderAssociationRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryOrderAssociationService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryOrderAssociationService.class);

    @Autowired
    private DeliveryOrderAssociationRepository deliveryOrderAssociationRepository;

    public List<DeliveryOrderAssociationDTO> findByDeliveryId(UUID deliveryId) {
        logger.info("Finding delivery order associations by delivery ID: {}", deliveryId);

        if (deliveryId == null) {
            logger.error("Delivery ID is null. Unable to find delivery order associations.");
            return null;
        }

        List<DeliveryOrderAssociation> deliveryOrderAssociations = deliveryOrderAssociationRepository.findByDelivery_DeliveryId(deliveryId);
        return DTOConversionUtil.convertToDeliveryOrderAssociationDTOList(deliveryOrderAssociations);
    }

    public List<DeliveryOrderAssociationDTO> findByOrderId(UUID orderId) {
        logger.info("Finding delivery order associations by order ID: {}", orderId);

        if (orderId == null) {
            logger.error("Order ID is null. Unable to find delivery order associations.");
            return null;
        }

        List<DeliveryOrderAssociation> deliveryOrderAssociations = deliveryOrderAssociationRepository.findByOrder_OrderId(orderId);
        return DTOConversionUtil.convertToDeliveryOrderAssociationDTOList(deliveryOrderAssociations);
    }

    public DeliveryOrderAssociationDTO saveDeliveryOrderAssociation(DeliveryOrderAssociationDTO deliveryOrderAssociationDTO) {
        logger.info("Saving delivery order association: {}", deliveryOrderAssociationDTO);

        if (deliveryOrderAssociationDTO == null) {
            logger.error("Delivery order association DTO is null. Unable to save.");
            return null;
        }

        // You can add additional validation checks if needed

        DeliveryOrderAssociation deliveryOrderAssociation = DTOConversionUtil.convertToDeliveryOrderAssociation(deliveryOrderAssociationDTO);
        DeliveryOrderAssociation savedDeliveryOrderAssociation = deliveryOrderAssociationRepository.save(deliveryOrderAssociation);

        return DTOConversionUtil.convertToDeliveryOrderAssociationDTO(savedDeliveryOrderAssociation);
    }

    public void deleteDeliveryOrderAssociation(UUID deliveryId, UUID orderId) {
        logger.info("Deleting delivery order association with Delivery ID: {} and Order ID: {}", deliveryId, orderId);

        if (deliveryId == null || orderId == null) {
            logger.error("Delivery ID or Order ID is null. Unable to delete delivery order association.");
            return;
        }

        deliveryOrderAssociationRepository.deleteByDelivery_DeliveryIdAndOrder_OrderId(deliveryId, orderId);
    }

}

