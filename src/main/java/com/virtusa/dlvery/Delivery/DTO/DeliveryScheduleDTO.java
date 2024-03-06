package com.virtusa.dlvery.Delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryScheduleDTO {

    private UUID scheduleId;

    private UUID agentId;

    private UUID deliveryId;

    private LocalDate deliveryDate;

    private boolean isCompleted;

    private LocalDate assignedDate;

    private String deliveryData;

    private UUID priorityId;

    private boolean isRescheduled;
}
