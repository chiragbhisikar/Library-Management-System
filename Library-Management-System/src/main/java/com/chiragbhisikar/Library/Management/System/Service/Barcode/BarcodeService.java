package com.chiragbhisikar.Library.Management.System.Service.Barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class BarcodeService implements iBarcodeService {
    @Override
    public String generateBarcodeNumber(Long bookId) {
        return "BOOK-" + bookId + "-" + System.currentTimeMillis();
    }


    @Override
    public String generateBarcodeUrl(String barcodeNumber) {
        String barcodeUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        barcodeUrl = barcodeUrl.substring(0, barcodeUrl.lastIndexOf("/api")) + "/images/barcode/" + barcodeNumber + ".png";

        return barcodeUrl;
    }

    @Override
    public String generateBarcodeImage(String barcodeNumber) throws WriterException, IOException {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/public/images/barcode/" + barcodeNumber + ".png";
        int width = 800, height = 300;

        // Generate barcode using Code128Writer
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeNumber, BarcodeFormat.CODE_128, width, height);

        // Convert BitMatrix to BufferedImage
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Add space below the barcode for text
        int textHeight = 60; // Space for text
        BufferedImage combinedImage = new BufferedImage(width, height + textHeight, BufferedImage.TYPE_INT_RGB);

        // Draw barcode and text
        Graphics2D graphics = combinedImage.createGraphics();

        // Background color
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height + textHeight);

        // Draw barcode
        graphics.drawImage(barcodeImage, 0, 10, null);

        // Draw text below the barcode
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.PLAIN, 20));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(barcodeNumber);
        int x = (width - textWidth) / 2; // Center align text
        int y = height + fontMetrics.getAscent() + 15; // Text below barcode
        graphics.drawString(barcodeNumber, x, y);
        graphics.dispose();

        // Write image to file
        Path path = Paths.get(filePath);

        // Save the image to a file
        File outputFile = path.toFile();
        if (!ImageIO.write(combinedImage, "PNG", outputFile)) {
            throw new IOException("Could not write image to file");
        }

//        building url
        return generateBarcodeUrl(barcodeNumber);
    }
}

