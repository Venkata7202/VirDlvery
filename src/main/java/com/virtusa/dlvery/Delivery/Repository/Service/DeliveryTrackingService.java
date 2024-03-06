package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryTrackingDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliveryTracking;
import com.virtusa.dlvery.Delivery.Repository.DeliveryTrackingRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryTrackingService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryTrackingService.class);

    @Autowired
    private DeliveryTrackingRepository deliveryTrackingRepository;

    public List<DeliveryTrackingDTO> findByDeliveryId(UUID deliveryId) {
        logger.info("Finding delivery tracking records by delivery ID: {}", deliveryId);

        if (deliveryId == null) {
            logger.error("Delivery ID is null. Unable to find delivery tracking records.");
            return null;
        }

        List<DeliveryTracking> deliveryTrackingList = deliveryTrackingRepository.findByDelivery_DeliveryId(deliveryId);

        return DTOConversionUtil.convertToDeliveryTrackingDTOList(deliveryTrackingList);
    }

    public List<DeliveryTrackingDTO> findByCustomerName(String customerName) {
        logger.info("Finding delivery tracking records by customer name: {}", customerName);

        if (customerName == null) {
            logger.error("Customer name is null. Unable to find delivery tracking records.");
            return null;
        }

        List<DeliveryTracking> deliveryTrackingList = deliveryTrackingRepository.findByCustomerName(customerName);

        return DTOConversionUtil.convertToDeliveryTrackingDTOList(deliveryTrackingList);
    }

    public List<DeliveryTrackingDTO> findByDoorLockStatus(boolean doorLockStatus) {
        logger.info("Finding delivery tracking records by door lock status: {}", doorLockStatus);

        List<DeliveryTracking> deliveryTrackingList = deliveryTrackingRepository.findByDoorLockStatus(doorLockStatus);

        return DTOConversionUtil.convertToDeliveryTrackingDTOList(deliveryTrackingList);
    }

    public List<DeliveryTrackingDTO> findByIsMissedDelivery(boolean isMissedDelivery) {
        logger.info("Finding delivery tracking records by missed delivery status: {}", isMissedDelivery);

        List<DeliveryTracking> deliveryTrackingList = deliveryTrackingRepository.findByIsMissedDelivery(isMissedDelivery);

        return DTOConversionUtil.convertToDeliveryTrackingDTOList(deliveryTrackingList);
    }

    public List<DeliveryTrackingDTO> findByIsDamagedDelivery(boolean isDamagedDelivery) {
        logger.info("Finding delivery tracking records by damaged delivery status: {}", isDamagedDelivery);

        List<DeliveryTracking> deliveryTrackingList = deliveryTrackingRepository.findByIsDamagedDelivery(isDamagedDelivery);

        return DTOConversionUtil.convertToDeliveryTrackingDTOList(deliveryTrackingList);
    }

    public List<DeliveryTrackingDTO> findByIsReturnDelivery(boolean isReturnDelivery) {
        logger.info("Finding delivery tracking records by return delivery status: {}", isReturnDelivery);

        List<DeliveryTracking> deliveryTrackingList = deliveryTrackingRepository.findByIsReturnDelivery(isReturnDelivery);

        return DTOConversionUtil.convertToDeliveryTrackingDTOList(deliveryTrackingList);
    }


}

