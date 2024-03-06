package com.virtusa.dlvery.Delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryOrderAssociationDTO {

    private UUID deliveryId;

    private UUID orderId;
}
