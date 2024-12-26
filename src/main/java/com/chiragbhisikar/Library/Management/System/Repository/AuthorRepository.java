package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);

    @Query("select p from Author p where LOWER(p.name) LIKE LOWER(CONCAT( '%' ,:name, '%'))")
    Optional<Author> findByName(String name);

    boolean existsByName(String name);
}
