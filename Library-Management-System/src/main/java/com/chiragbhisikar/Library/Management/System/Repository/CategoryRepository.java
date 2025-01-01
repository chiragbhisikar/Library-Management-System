package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select p from Category p where LOWER(p.name) LIKE LOWER(CONCAT( '%' ,:name, '%'))")
    Optional<Category> findByName(String name);

    Optional<Category> findById(Long id);

    boolean existsByName(String name);
}
