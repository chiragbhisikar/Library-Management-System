package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    /**
     * @Query("SELECT p from Product p WHERE " + "LOWER(p.name) LIKE LOWER(CONCAT( '%' ,:keyword, '%')) OR " + "LOWER(p.brand) LIKE LOWER(CONCAT( '%' ,:keyword, '%')) OR " + "LOWER(p.description) LIKE LOWER(CONCAT( '%' ,:keyword, '%')) OR " + "LOWER(p.category) LIKE LOWER(CONCAT( '%' ,:keyword, '%'))")
     */

    @Query("select p from Publication p where LOWER(p.name) LIKE LOWER(CONCAT( '%' ,:name, '%'))")
    Optional<Publication> findByName(String name);

    boolean existsByName(String name);
}
