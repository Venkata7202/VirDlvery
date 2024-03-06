package com.virtusa.dlvery.Delivery.Repository.Service;

import com.virtusa.dlvery.Delivery.DTO.DeliveryScheduleDTO;
import com.virtusa.dlvery.Delivery.Entities.DeliverySchedule;
import com.virtusa.dlvery.Delivery.Repository.DeliveryScheduleRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryScheduleService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryScheduleService.class);

    @Autowired
    private DeliveryScheduleRepository deliveryScheduleRepository;

    public List<DeliveryScheduleDTO> findByAgentId(UUID agentId) {
        logger.info("Finding delivery schedules by agent ID: {}", agentId);

        if (agentId == null) {
            logger.error("Agent ID is null. Unable to find delivery schedules.");
            return null;
        }

        List<DeliverySchedule> deliverySchedules = deliveryScheduleRepository.findByAgent_AgentId(agentId);

        return DTOConversionUtil.convertToDeliveryScheduleDTOList(deliverySchedules);
    }

    public List<DeliveryScheduleDTO> findByDeliveryId(UUID deliveryId) {
        logger.info("Finding delivery schedules by delivery ID: {}", deliveryId);

        if (deliveryId == null) {
            logger.error("Delivery ID is null. Unable to find delivery schedules.");
            return null;
        }

        List<DeliverySchedule> deliverySchedules = deliveryScheduleRepository.findByDelivery_DeliveryId(deliveryId);

        return DTOConversionUtil.convertToDeliveryScheduleDTOList(deliverySchedules);
    }

    public List<DeliveryScheduleDTO> findByIsCompleted(boolean isCompleted) {
        logger.info("Finding delivery schedules by completion status: {}", isCompleted);

        List<DeliverySchedule> deliverySchedules = deliveryScheduleRepository.findByIsCompleted(isCompleted);

        return DTOConversionUtil.convertToDeliveryScheduleDTOList(deliverySchedules);
    }

    public List<DeliveryScheduleDTO> findByIsRescheduled(boolean isRescheduled) {
        logger.info("Finding delivery schedules by reschedule status: {}", isRescheduled);

        List<DeliverySchedule> deliverySchedules = deliveryScheduleRepository.findByIsRescheduled(isRescheduled);

        return DTOConversionUtil.convertToDeliveryScheduleDTOList(deliverySchedules);
    }

   }



