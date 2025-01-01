package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.BookIssue;
import com.chiragbhisikar.Library.Management.System.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    BookIssue findBookIssuesById(Long id);

    List<BookIssue> findBookIssuesByUser(User user);

    //    @Query("select * from BookIssue p where p.copy = :copy and p.is_returned = false")
    @Query(value = "SELECT * FROM book_issues WHERE copy_id = :copyId AND is_returned = false LIMIT 1", nativeQuery = true)
    Optional<BookIssue> findUnreturnedBooksByCopyId(@Param("copyId") Long copyId);
}
