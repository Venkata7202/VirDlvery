package com.virtusa.dlvery.Delivery.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeliveryAgentDTO {

    private UUID agentId;

    private String agentName;
}

