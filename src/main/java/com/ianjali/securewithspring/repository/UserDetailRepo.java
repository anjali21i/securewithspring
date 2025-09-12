package com.ianjali.securewithspring.repository;

import com.ianjali.securewithspring.entities.UserDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<UserDetail> findByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<UserDetail> findByUserNameIgnoreCase(String userName);
}
