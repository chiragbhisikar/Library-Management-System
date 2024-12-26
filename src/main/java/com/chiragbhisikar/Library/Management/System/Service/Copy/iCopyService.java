package com.chiragbhisikar.Library.Management.System.Service.Copy;

import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Copy;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface iCopyService {
    Copy getCopyById(Long id);

    Copy getCopyByBarcode(String bookBarcodeNumber);

    List<Copy> getAllCopy();

    String addCopy(Copy copy) throws AlreadyExistsException, NotFoundException, WriterException, IOException;

    List<Map<String, String>> addCopies(Copy copy, int numberOfCopies) throws AlreadyExistsException, NotFoundException, WriterException, IOException;

    Copy updateCopy(Copy copy, Long id);

    void deleteCopy(Long id);
}
