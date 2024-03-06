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
public class DeliveryTrackingDTO {

    private UUID trackingId;

    private UUID deliveryId;

    private String customerName;

    private byte[] customerSignature;

    private boolean doorLockStatus;

    private boolean isMissedDelivery;

    private boolean isDamagedDelivery;

    private boolean isReturnDelivery;

    private LocalDate createdAt;
}
