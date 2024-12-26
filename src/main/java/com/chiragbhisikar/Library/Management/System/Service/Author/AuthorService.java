package com.chiragbhisikar.Library.Management.System.Service.Author;

import com.chiragbhisikar.Library.Management.System.DTO.AuthorDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Author;
import com.chiragbhisikar.Library.Management.System.Repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService implements iAuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found!"));
    }


    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Author not found!"));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Author addAuthor(Author author) {
        return Optional.of(author).filter(c -> !authorRepository.existsByName(c.getName()))
                .map(authorRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(author.getName() + " already exists"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Author updateAuthor(Author author, Long id) {
        return Optional.ofNullable(getAuthorById(id)).map(oldCategory -> {
            oldCategory.setName(author.getName());
            return authorRepository.save(oldCategory);
        }).orElseThrow(() -> new NotFoundException("Author not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteAuthor(Long id) {
        authorRepository.findById(id)
                .ifPresentOrElse(authorRepository::delete, () -> {
                    throw new NotFoundException("Author not found!");
                });
    }

    @Override
    public AuthorDto convertAuthorToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public List<AuthorDto> convertAuthorsToDto(List<Author> authors) {
        return authors.stream().map(this::convertAuthorToDto).collect(Collectors.toList());
    }
}
