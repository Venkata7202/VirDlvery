package com.virtusa.dlvery.Delivery.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryAgentDeviceDTO {

    private UUID deviceId;

    private DeliveryAgentDTO agent;

    private String deviceName;

    private String deviceType;
}
