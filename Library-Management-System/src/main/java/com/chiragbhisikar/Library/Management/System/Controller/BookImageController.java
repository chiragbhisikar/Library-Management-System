package com.chiragbhisikar.Library.Management.System.Controller;

import com.chiragbhisikar.Library.Management.System.DTO.BookImageDto;
import com.chiragbhisikar.Library.Management.System.Model.BookImage;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.BookImage.BookImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-images")
public class BookImageController {
    private final BookImageService bookImageService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFiles(
            @RequestParam("images") MultipartFile[] files,
            @RequestParam("book") Long bookId) {
        try {
            List<BookImage> bookImages = bookImageService.uploadFiles(files, bookId);
            List<BookImageDto> bookImageDtos = bookImageService.convertBookImagesToDto(bookImages);

            return ResponseEntity.ok(new ApiResponse("Image Uploaded Successfully!", bookImageDtos));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while uploading files: " + e.getMessage(), null));
        }
    }
}
