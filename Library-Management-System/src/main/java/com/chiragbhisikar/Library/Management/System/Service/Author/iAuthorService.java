package com.chiragbhisikar.Library.Management.System.Service.Author;

import com.chiragbhisikar.Library.Management.System.DTO.AuthorDto;
import com.chiragbhisikar.Library.Management.System.Model.Author;

import java.util.List;

public interface iAuthorService {
    Author getAuthorById(Long id);

    Author getAuthorByName(String name);

    List<Author> getAllAuthors();

    Author addAuthor(Author author);

    Author updateAuthor(Author author, Long id);

    void deleteAuthor(Long id);

    AuthorDto convertAuthorToDto(Author author);

    List<AuthorDto> convertAuthorsToDto(List<Author> authors);
}
