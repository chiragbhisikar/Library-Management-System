package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //    @Query("select p from Book p where LOWER(p.title) LIKE LOWER(CONCAT( '%' ,:name, '%'))")
    @Query("""
            SELECT b FROM Book b
                            WHERE (:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
                            OR (:keyword IS NULL OR LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
                            OR (:keyword IS NULL OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%')))
                            OR (:keyword IS NULL OR LOWER(b.category.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
                            OR (:keyword IS NULL OR LOWER(b.author.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
                            OR (:keyword IS NULL OR LOWER(b.language.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            
            """)
    List<Book> searchBooks(String keyword);

    boolean existsByTitle(String name);

}
