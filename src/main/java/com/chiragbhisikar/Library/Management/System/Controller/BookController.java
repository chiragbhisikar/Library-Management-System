package com.chiragbhisikar.Library.Management.System.Controller;

import com.chiragbhisikar.Library.Management.System.DTO.BookDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Book;
import com.chiragbhisikar.Library.Management.System.Request.Book.AddBookRequest;
import com.chiragbhisikar.Library.Management.System.Request.Book.UpdateBookRequest;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            List<BookDto> bookDto = bookService.convertBooksToDto(books);

            return ResponseEntity.ok(new ApiResponse("Success", bookDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable Long bookId) {
        try {
            Book book = bookService.getBookById(bookId);
            BookDto bookDto = bookService.convertBookToDto(book);

            return ResponseEntity.ok(new ApiResponse("Success", bookDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam(name = "keyword") String keyword) {
        try {
            System.out.println(keyword);
            List<Book> books = bookService.searchBook(keyword);
            List<BookDto> bookDto = bookService.convertBooksToDto(books);

            return ResponseEntity.ok(new ApiResponse("Success", bookDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-book")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody AddBookRequest book) {
        try {
            Book newBook = bookService.addBook(book);
            BookDto bookDto = bookService.convertBookToDto(newBook);

            return ResponseEntity.ok(new ApiResponse("Books Added Successfully !", bookDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-book/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody UpdateBookRequest book, @PathVariable Long id) {
        try {
            Book existingBook = bookService.updateBook(book, id);
            BookDto bookDto = bookService.convertBookToDto(existingBook);

            return ResponseEntity.ok(new ApiResponse("Book Updated Successfully !", bookDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok(new ApiResponse("Book Deleted Successfully !", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
