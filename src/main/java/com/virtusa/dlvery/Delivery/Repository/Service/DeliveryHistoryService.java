package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryHistoryDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliveryHistory;
import com.virtusa.dlvery.Delivery.Enum.EventType;
import com.virtusa.dlvery.Delivery.Repository.DeliveryHistoryRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryHistoryService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryHistoryService.class);

    @Autowired
    private DeliveryHistoryRepository deliveryHistoryRepository;

    public List<DeliveryHistoryDTO> findByScheduleId(UUID scheduleId) {
        logger.info("Finding delivery history by schedule ID: {}", scheduleId);

        if (scheduleId == null) {
            logger.error("Schedule ID is null. Unable to find delivery history.");
            return null;
        }

        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findBySchedule_ScheduleId(scheduleId);
        return DTOConversionUtil.convertToDeliveryHistoryDTOList(deliveryHistories);
    }

    public List<DeliveryHistoryDTO> findByEventType(EventType eventType) {
        logger.info("Finding delivery history by event type: {}", eventType);

        if (eventType == null) {
            logger.error("Event type is null. Unable to find delivery history.");
            return null;
        }

        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findByEventType(eventType);
        return DTOConversionUtil.convertToDeliveryHistoryDTOList(deliveryHistories);
    }

    public List<DeliveryHistoryDTO> findByDeliveryAgentName(String deliveryAgentName) {
        logger.info("Finding delivery history by delivery agent name: {}", deliveryAgentName);

        if (deliveryAgentName == null) {
            logger.error("Delivery agent name is null. Unable to find delivery history.");
            return null;
        }

        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findByDeliveryAgentName(deliveryAgentName);
        return DTOConversionUtil.convertToDeliveryHistoryDTOList(deliveryHistories);
    }

    public List<DeliveryHistoryDTO> findByCustomerName(String customerName) {
        logger.info("Finding delivery history by customer name: {}", customerName);

        if (customerName == null) {
            logger.error("Customer name is null. Unable to find delivery history.");
            return null;
        }

        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findByCustomerName(customerName);
        return DTOConversionUtil.convertToDeliveryHistoryDTOList(deliveryHistories);
    }

    public List<DeliveryHistoryDTO> findEventsBeforeTimestamp(LocalDateTime eventTimestamp) {
        logger.info("Finding delivery history events before timestamp: {}", eventTimestamp);

        if (eventTimestamp == null) {
            logger.error("Event timestamp is null. Unable to find delivery history.");
            return null;
        }

        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findEventsBeforeTimestamp(eventTimestamp);
        return DTOConversionUtil.convertToDeliveryHistoryDTOList(deliveryHistories);
    }

    public Optional<DeliveryHistoryDTO> findById(UUID id) {
        logger.info("Finding delivery history by ID: {}", id);

        if (id == null) {
            logger.error("ID is null. Unable to find delivery history.");
            return Optional.empty();
        }

        Optional<DeliveryHistory> deliveryHistory = deliveryHistoryRepository.findById(id);
        return deliveryHistory.map(DTOConversionUtil::convertToDeliveryHistoryDTO);
    }

    public DeliveryHistoryDTO saveDeliveryHistory(DeliveryHistoryDTO deliveryHistoryDTO) {
        logger.info("Saving delivery history: {}", deliveryHistoryDTO);

        if (deliveryHistoryDTO == null) {
            logger.error("Delivery history DTO is null. Unable to save.");
            return null;
        }

        DeliveryHistory deliveryHistory = DTOConversionUtil.convertToDeliveryHistory(deliveryHistoryDTO);
        DeliveryHistory savedDeliveryHistory = deliveryHistoryRepository.save(deliveryHistory);

        return DTOConversionUtil.convertToDeliveryHistoryDTO(savedDeliveryHistory);
    }

    public void deleteById(UUID id) {
        logger.info("Deleting delivery history by ID: {}", id);

        if (id == null) {
            logger.error("ID is null. Unable to delete delivery history.");
            return;
        }

        deliveryHistoryRepository.deleteById(id);
    }
}

