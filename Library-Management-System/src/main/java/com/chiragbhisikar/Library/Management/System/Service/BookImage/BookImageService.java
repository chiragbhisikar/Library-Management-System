package com.chiragbhisikar.Library.Management.System.Service.BookImage;

import com.chiragbhisikar.Library.Management.System.DTO.BookImageDto;
import com.chiragbhisikar.Library.Management.System.Model.Book;
import com.chiragbhisikar.Library.Management.System.Model.BookImage;
import com.chiragbhisikar.Library.Management.System.Repository.BookImageRepository;
import com.chiragbhisikar.Library.Management.System.Service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookImageService implements iBookImageService {
    private final BookImageRepository bookImageRepository;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Override
    public List<BookImage> uploadFiles(MultipartFile[] files, Long bookId) throws IOException {
        Book book = bookService.getBookById(bookId);
        List<BookImage> bookImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // Saving Image In Local Storage
                String filePath = saveFile(file);

                // Saving BookImage In Database
                BookImage bookImage = new BookImage();
                bookImage.setBook(book);
                bookImage.setFilePath(filePath);
                bookImageRepository.save(bookImage);

                bookImages.add(bookImage);
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }

        return bookImages;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/public/images/book-images/";

        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;
        File targetFile = new File(uploadDir + fileName);
        file.transferTo(targetFile);

        String imageUrl = generateImageUrl(fileName);

        return imageUrl;
    }

    public String generateImageUrl(String fileName) {
        String uploadDir = "/images/book-images/" + fileName;
        return uploadDir;
    }


    public BookImageDto convertBookImageToDto(BookImage bookImage) {
        return modelMapper.map(bookImage, BookImageDto.class);
    }

    public List<BookImageDto> convertBookImagesToDto(List<BookImage> bookImages) {
        return bookImages.stream().map(this::convertBookImageToDto).collect(Collectors.toList());
    }
}
