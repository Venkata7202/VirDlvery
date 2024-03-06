package com.virtusa.dlvery.Delivery.DTO;

import com.virtusa.dlvery.Delivery.Enum.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryHistoryDTO {

    private UUID historyId;

    private UUID scheduleId;

    private EventType eventType;

    private String eventDescription;

    private LocalDateTime eventTimestamp;

    private String deliveryAgentName;

    private String customerName;
}
