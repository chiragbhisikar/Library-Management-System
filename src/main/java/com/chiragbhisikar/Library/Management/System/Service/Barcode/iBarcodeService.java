package com.chiragbhisikar.Library.Management.System.Service.Barcode;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface iBarcodeService {
    public String generateBarcodeNumber(Long bookId);

    public String generateBarcodeUrl(String barcodeNumber);

    public String generateBarcodeImage(String text) throws WriterException, IOException;
}
