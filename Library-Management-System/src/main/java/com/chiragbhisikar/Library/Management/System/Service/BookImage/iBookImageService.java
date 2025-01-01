package com.chiragbhisikar.Library.Management.System.Service.BookImage;

import com.chiragbhisikar.Library.Management.System.Model.BookImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface iBookImageService {
    List<BookImage> uploadFiles(MultipartFile[] files, Long bookId) throws IOException;

    String saveFile(MultipartFile file) throws IOException;
}