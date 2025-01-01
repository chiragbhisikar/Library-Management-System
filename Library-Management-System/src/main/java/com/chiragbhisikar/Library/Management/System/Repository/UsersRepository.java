package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);

    Optional<User> findByEnrollmentNo(Long enrollmentNo);

    boolean existsByEnrollmentNo(Long enrollment_no);
}