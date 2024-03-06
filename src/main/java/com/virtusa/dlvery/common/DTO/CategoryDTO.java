package com.virtusa.dlvery.common.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CategoryDTO {

    private UUID categoryId;
    private String categoryName;

}
