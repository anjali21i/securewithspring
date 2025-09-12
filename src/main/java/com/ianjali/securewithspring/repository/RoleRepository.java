package com.ianjali.securewithspring.repository;

import com.ianjali.securewithspring.entities.Roles;
import com.ianjali.securewithspring.utility.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(Constants.RoleName roleName);
}
