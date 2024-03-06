package com.virtusa.dlvery.common.Util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BarcodeGenerator {

    public static byte[] generateBarcode(UUID productId) {
        String id = productId.toString();
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(id, BarcodeFormat.CODE_128, 200, 50);

            BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 50; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String decodeBarcode(byte[] barcodeImage) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(barcodeImage);
            BufferedImage image = ImageIO.read(inputStream);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            MultiFormatReader reader = new MultiFormatReader();
            com.google.zxing.Result result = reader.decode(binaryBitmap);

            return result.getText();
        } catch (NotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

