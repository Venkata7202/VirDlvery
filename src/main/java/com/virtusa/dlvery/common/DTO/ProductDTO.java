package com.virtusa.dlvery.common.DTO;

import com.virtusa.dlvery.Inventory.DTO.WarehouseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDTO {

    private UUID productId;
    private String productName;
    private CategoryDTO category;
    private boolean isPerishable;
    private LocalDate expiryDate;
    private boolean isDamaged;
    private WarehouseDTO warehouse;

}


