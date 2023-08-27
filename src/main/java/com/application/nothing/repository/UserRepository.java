package com.application.nothing.repository;

import com.application.nothing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Custom queries can be added here. For example:
    // Optional<User> findByUsername(String username);
}

