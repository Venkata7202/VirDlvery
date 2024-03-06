package com.virtusa.dlvery.Inventory.DTO;

import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import com.virtusa.dlvery.Inventory.Enum.MovementType;
import com.virtusa.dlvery.common.Entities.Product;
import com.virtusa.dlvery.common.Repository.ProductRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InventoryDTO {

    private UUID movementId;
    private Product product;
    private int quantityIn;
    private int quantityOut;
    private MovementType movementType;
    private String reason;
    private LocalDate date;
    private Warehouse warehouse;

}
