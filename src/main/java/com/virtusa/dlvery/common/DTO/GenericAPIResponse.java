package com.virtusa.dlvery.common.DTO;


@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
@lombok.ToString
public class GenericAPIResponse {
    private String message;
    private int status;
}
