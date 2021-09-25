package com.softwarejm.demojava17.repository;

import com.softwarejm.demojava17.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
