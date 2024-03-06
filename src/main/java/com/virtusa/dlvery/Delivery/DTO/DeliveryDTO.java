package com.virtusa.dlvery.Delivery.DTO;

import com.virtusa.dlvery.Delivery.Enum.DeliveryStatus;
import com.virtusa.dlvery.Inventory.DTO.WarehouseDTO;
import com.virtusa.dlvery.common.DTO.ProductDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDTO {

    private UUID deliveryId;

    private ProductDTO product;

    private DeliveryAgentDTO deliveryAgent;

    private DeliveryStatus status;

    private LocalDate date;

    private boolean isDamaged;

    private WarehouseDTO warehouse;
}
