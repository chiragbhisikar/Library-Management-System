package com.chiragbhisikar.Library.Management.System.Service.Copy;

import com.chiragbhisikar.Library.Management.System.DTO.CopyDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Book;
import com.chiragbhisikar.Library.Management.System.Model.Copy;
import com.chiragbhisikar.Library.Management.System.Repository.CopyRepository;
import com.chiragbhisikar.Library.Management.System.Service.Barcode.BarcodeService;
import com.chiragbhisikar.Library.Management.System.Service.Book.BookService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CopyService implements iCopyService {
    private final CopyRepository copyRepository;
    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final BarcodeService barcodeService;

    @Override
    public List<Copy> getAllCopy() {
        return copyRepository.findAll();
    }

    @Override
    public Copy getCopyById(Long id) {
        return copyRepository.findById(id).orElseThrow(() -> new NotFoundException("Copy not found!"));
    }


    @Override
    public Copy getCopyByBarcode(String bookBarcodeNumber) {
        return copyRepository.findByBookBarcodeNumber(bookBarcodeNumber).orElseThrow(() -> new NotFoundException("Copy not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public String addCopy(Copy copy) throws AlreadyExistsException, NotFoundException, WriterException, IOException {
        String barcodeNumber = barcodeService.generateBarcodeNumber(copy.getBook().getId());

        // Set barcode number in the copy entity
        copy.setBookBarcodeNumber(barcodeNumber);
        Book book = bookService.getBookById(copy.getBook().getId());
        copy.setBook(book);

        Optional.of(copy).filter(c -> !copyRepository.existsByBookBarcodeNumber(c.getBookBarcodeNumber())).map(copyRepository::save).orElseThrow(() -> new AlreadyExistsException(copy.getBookBarcodeNumber() + " already exists"));

        //        generate barcode
        String barcodeUrl = barcodeService.generateBarcodeImage(barcodeNumber);

        return barcodeUrl;

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Map<String, String>> addCopies(Copy copy, int numberOfCopies) throws AlreadyExistsException, NotFoundException, WriterException, IOException {
        Long time = System.currentTimeMillis();
        List<Map<String, String>> barcodeUrls = new ArrayList<>();

        for (int i = 1; i <= numberOfCopies; i++) {
            Copy newCopy = new Copy();
            String barcodeNumber = "BOOK-" + copy.getBook().getId() + "-" + time + i;

            // Set barcode number in the copy entity
            newCopy.setBookBarcodeNumber(barcodeNumber);
            Book book = bookService.getBookById(copy.getBook().getId());
            newCopy.setBook(book);

            //        generate barcode
            String barcodeUrl = barcodeService.generateBarcodeImage(barcodeNumber);

            barcodeUrls.add(Map.of("url", barcodeUrl));

            Optional.of(newCopy).filter(c -> !copyRepository.existsByBookBarcodeNumber(c.getBookBarcodeNumber())).map(copyRepository::save).orElseThrow(() -> new AlreadyExistsException(copy.getBookBarcodeNumber() + " already exists"));
        }


        return barcodeUrls;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Copy updateCopy(Copy copy, Long id) {
        return Optional.ofNullable(getCopyById(id)).map(oldCopy -> {
            oldCopy.setBook(copy.getBook());
            if (copy.getBookBarcodeNumber() != null) {
                oldCopy.setBookBarcodeNumber(copy.getBookBarcodeNumber());
            }
            return copyRepository.save(oldCopy);
        }).orElseThrow(() -> new NotFoundException("Copy not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteCopy(Long id) {
        copyRepository.findById(id).ifPresentOrElse(copyRepository::delete, () -> {
            throw new NotFoundException("Copy not found!");
        });
    }

    public CopyDto convertCopyToDto(Copy copy) {
        return modelMapper.map(copy, CopyDto.class);
    }

    public List<CopyDto> convertCopiesToDto(List<Copy> copies) {
        return copies.stream().map(this::convertCopyToDto).collect(Collectors.toList());
    }

    public Copy createCopy(Copy copy) throws IOException {
        // Generate a unique barcode number
        String barcodeNumber = barcodeService.generateBarcodeNumber(copy.getBook().getId());

        // Set barcode number in the copy entity
        copy.setBookBarcodeNumber(barcodeNumber);

        // Save the copy to the database
        return copyRepository.save(copy);
    }
}
