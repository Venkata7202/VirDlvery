package com.virtusa.dlvery.Inventory.DTO;

import com.virtusa.dlvery.common.DTO.ProductDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class WarehouseRequestDTO {

    private UUID warehouseId;
    private String warehouseName;
    private String location;
    private int capacity;

}
