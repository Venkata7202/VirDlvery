package com.virtusa.dlvery.common.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductRequestDTO {

    private UUID productId;
    private String productName;
    private UUID categoryId;
    private boolean isPerishable;
    private LocalDate expiryDate;
    private boolean isDamaged;
    private UUID warehouseId;

}
