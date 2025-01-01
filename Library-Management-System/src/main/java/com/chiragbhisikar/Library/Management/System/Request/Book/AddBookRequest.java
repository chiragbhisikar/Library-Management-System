package com.chiragbhisikar.Library.Management.System.Request.Book;

import lombok.Data;

import java.time.Year;

@Data
public class AddBookRequest {
    private Long id;
    private String title;
    private String isbn;
    private int totalPages;
    private int edition;
    private Year publicationYear;
    private int noOfAvailableBooks;
    private int perDayPenalty;
    private String description;


    private Long categoryId;
    private Long publicationId;
    private Long authorId;
    private Long languageId;

}
