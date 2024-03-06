package com.virtusa.dlvery.common.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class BarcodeDTO {

    private String productId;
    private byte[] barcodeImage;

    public BarcodeDTO(String productId, byte[] barcodeImage) {
        this.productId = productId;
        this.barcodeImage = barcodeImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public byte[] getBarcodeImage() {
        return barcodeImage;
    }

    public void setBarcodeImage(byte[] barcodeImage) {
        this.barcodeImage = barcodeImage;
    }
}

