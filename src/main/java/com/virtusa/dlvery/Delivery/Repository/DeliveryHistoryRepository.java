package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliveryHistory;
import com.virtusa.dlvery.Delivery.Enum.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, UUID> {

    List<DeliveryHistory> findBySchedule_ScheduleId(UUID scheduleId);

    List<DeliveryHistory> findByEventType(EventType eventType);

    List<DeliveryHistory> findByDeliveryAgentName(String deliveryAgentName);

    List<DeliveryHistory> findByCustomerName(String customerName);

    @Query("SELECT dh FROM DeliveryHistory dh WHERE dh.eventTimestamp < :eventTimestamp")
    List<DeliveryHistory> findEventsBeforeTimestamp(@Param("eventTimestamp") LocalDateTime eventTimestamp);

}
