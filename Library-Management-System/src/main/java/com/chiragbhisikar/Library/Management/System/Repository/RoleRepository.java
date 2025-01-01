package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
