package com.chiragbhisikar.Library.Management.System.Controller;

import com.chiragbhisikar.Library.Management.System.DTO.AuthorDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Author;
import com.chiragbhisikar.Library.Management.System.Repository.AuthorRepository;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.Author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllAuthors() {
        try {
            List<Author> authors = authorService.getAllAuthors();
            List<AuthorDto> authorDtos = authorService.convertAuthorsToDto(authors);

            return ResponseEntity.ok(new ApiResponse("Success", authorDtos));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponse> getAuthorById(@PathVariable Long authorId) {
        try {
            Author author = authorService.getAuthorById(authorId);
            AuthorDto authorDto = authorService.convertAuthorToDto(author);

            return ResponseEntity.ok(new ApiResponse("Success", authorDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse> getAuthorByName(@RequestParam(name = "name") String name) {
        try {
            Author author = authorService.getAuthorByName(name);
            AuthorDto authorDto = authorService.convertAuthorToDto(author);

            return ResponseEntity.ok(new ApiResponse("Success", authorDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-author")
    public ResponseEntity<ApiResponse> addAuthor(@RequestBody Author author) {
        try {
            Author newAuthor = authorService.addAuthor(author);
            AuthorDto authorDto = authorService.convertAuthorToDto(newAuthor);

            return ResponseEntity.ok(new ApiResponse("Author Added Successfully !", authorDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-author/{id}")
    public ResponseEntity<ApiResponse> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        try {
            Author newAuthor = authorService.updateAuthor(author, id);
            AuthorDto authorDto = authorService.convertAuthorToDto(newAuthor);

            return ResponseEntity.ok(new ApiResponse("Author Updated Successfully !", authorDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-author/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok(new ApiResponse("Author Deleted Successfully !", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
