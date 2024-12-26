package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("select p from Language p where LOWER(p.name) LIKE LOWER(CONCAT( '%' ,:name, '%'))")
    Optional<Language> findByName(String name);

    boolean existsByName(String name);
}
