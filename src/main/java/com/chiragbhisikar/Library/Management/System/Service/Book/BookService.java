package com.chiragbhisikar.Library.Management.System.Service.Book;

import com.chiragbhisikar.Library.Management.System.DTO.BookDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.*;
import com.chiragbhisikar.Library.Management.System.Repository.*;
import com.chiragbhisikar.Library.Management.System.Request.Book.AddBookRequest;
import com.chiragbhisikar.Library.Management.System.Request.Book.UpdateBookRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements iBookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final PublicationRepository publicationRepository;
    private final AuthorRepository authorRepository;
    private final LanguageRepository languageRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found!"));
    }


    @Override
    public List<Book> searchBook(String keyword) {
        return bookRepository.searchBooks(keyword);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Book addBook(AddBookRequest request) {
        return Optional.of(request).filter(user -> !bookRepository.existsByTitle(request.getTitle())).map(req -> {
            Book book = new Book();
            book.setTitle(request.getTitle());
            book.setIsbn(request.getIsbn());
            book.setTotalPages(request.getTotalPages());
            book.setEdition(request.getEdition());
            book.setPublicationYear(request.getPublicationYear());
            book.setNoOfAvailableBooks(request.getNoOfAvailableBooks());
            book.setDescription(request.getDescription());

            book.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found!")));
            book.setPublication(publicationRepository.findById(request.getPublicationId()).orElseThrow(() -> new NotFoundException("Publication not found!")));
            book.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found!")));
            book.setLanguage(languageRepository.findById(request.getLanguageId()).orElseThrow(() -> new NotFoundException("Language not found!")));

            return bookRepository.save(book);
        }).orElseThrow(() -> new AlreadyExistsException("Oops!" + request.getTitle() + " already exists!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Book updateBook(UpdateBookRequest request, Long id) {
        return bookRepository.findById(id).map(existingBook -> {
            request.getTitle().ifPresent(existingBook::setTitle);
            request.getIsbn().ifPresent(existingBook::setIsbn);
            request.getTotalPages().ifPresent(existingBook::setTotalPages);
            request.getEdition().ifPresent(existingBook::setEdition);
            request.getPublicationYear().ifPresent(existingBook::setPublicationYear);
            request.getNoOfAvailableBooks().ifPresent(existingBook::setNoOfAvailableBooks);
            request.getDescription().ifPresent(existingBook::setDescription);


            request.getCategoryId().ifPresent(categoryId -> {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new NotFoundException("Category not found!"));
                existingBook.setCategory(category);
            });

            request.getPublicationId().ifPresent(publicationId -> {
                Publication publication = publicationRepository.findById(publicationId)
                        .orElseThrow(() -> new NotFoundException("Publication not found!"));
                existingBook.setPublication(publication);
            });

            request.getAuthorId().ifPresent(authorId -> {
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new NotFoundException("Author not found!"));
                existingBook.setAuthor(author);
            });

            request.getLanguageId().ifPresent(languageId -> {
                Language language = languageRepository.findById(languageId)
                        .orElseThrow(() -> new NotFoundException("Language not found!"));
                existingBook.setLanguage(language);
            });

            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new NotFoundException("Book not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteBook(Long id) {
        bookRepository.findById(id).ifPresentOrElse(bookRepository::delete, () -> {
            throw new NotFoundException("Book not found!");
        });
    }

    @Override
    public BookDto convertBookToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public List<BookDto> convertBooksToDto(List<Book> books) {
        return books.stream().map(this::convertBookToDto).collect(Collectors.toList());
    }
}