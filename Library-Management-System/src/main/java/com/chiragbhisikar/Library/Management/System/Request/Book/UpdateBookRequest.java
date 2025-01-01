package com.chiragbhisikar.Library.Management.System.Request.Book;


import lombok.Data;

import java.time.Year;
import java.util.Optional;

@Data
public class UpdateBookRequest {
    private Optional<String> title;
    private Optional<String> isbn;
    private Optional<Integer> totalPages;
    private Optional<Integer> edition;
    private Optional<Year> publicationYear;
    private Optional<Integer> perDayPenalty;
    private Optional<Integer> noOfAvailableBooks;
    private Optional<String> description;
    
    private Optional<Long> categoryId;
    private Optional<Long> publicationId;
    private Optional<Long> authorId;
    private Optional<Long> languageId;
}
