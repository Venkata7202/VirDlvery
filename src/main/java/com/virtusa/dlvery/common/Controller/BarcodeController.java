package com.virtusa.dlvery.common.Controller;

import com.virtusa.dlvery.common.DTO.BarcodeDTO;
import com.virtusa.dlvery.common.Util.BarcodeGenerator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/barcode")
public class BarcodeController {
    @GetMapping(value = "/generates/{productIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BarcodeDTO> generateBarcodes(@PathVariable List<UUID> productIds) {
        return productIds.stream()
                .map(productId -> new BarcodeDTO(productId.toString(), BarcodeGenerator.generateBarcode(productId)))
                .collect(Collectors.toList());
    }
    @GetMapping(value = "/generate/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BarcodeDTO generateBarcode(@PathVariable UUID productId) {
        byte[] barcodeImage = BarcodeGenerator.generateBarcode(productId);
        return new BarcodeDTO(productId.toString(), barcodeImage);
    }
}