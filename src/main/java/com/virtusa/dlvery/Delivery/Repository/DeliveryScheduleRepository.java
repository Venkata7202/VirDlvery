package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliverySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryScheduleRepository extends JpaRepository<DeliverySchedule, UUID> {

    List<DeliverySchedule> findByAgent_AgentId(UUID agentId);

    List<DeliverySchedule> findByDelivery_DeliveryId(UUID deliveryId);

    List<DeliverySchedule> findByIsCompleted(boolean isCompleted);

    List<DeliverySchedule> findByIsRescheduled(boolean isRescheduled);
}

