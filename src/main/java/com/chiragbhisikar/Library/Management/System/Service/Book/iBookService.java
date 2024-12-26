package com.chiragbhisikar.Library.Management.System.Service.Book;

import com.chiragbhisikar.Library.Management.System.DTO.BookDto;
import com.chiragbhisikar.Library.Management.System.Model.Book;
import com.chiragbhisikar.Library.Management.System.Request.Book.AddBookRequest;
import com.chiragbhisikar.Library.Management.System.Request.Book.UpdateBookRequest;

import java.util.List;

public interface iBookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    List<Book> searchBook(String keyword);

    Book addBook(AddBookRequest book);

    Book updateBook(UpdateBookRequest book, Long id);

    void deleteBook(Long id);

    BookDto convertBookToDto(Book book);

    List<BookDto> convertBooksToDto(List<Book> books);
}
