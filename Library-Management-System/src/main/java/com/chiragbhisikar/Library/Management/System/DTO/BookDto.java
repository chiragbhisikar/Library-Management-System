package com.chiragbhisikar.Library.Management.System.DTO;

import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private int totalPages;
    private int edition;
    private Year publicationYear;
    private int noOfAvailableBooks;
    private String description;

    private CategoryDto category;
    private PublicationDto publication;
    private AuthorDto author;
    private LanguageDto language;
    private List<BookImageDto> images;
}
