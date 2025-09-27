package com.security.securityPractice.repository;

import com.security.securityPractice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String name);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
}
