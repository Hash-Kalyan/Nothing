package com.application.nothing.repository;

import com.application.nothing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);

    // Custom queries can be added here. For example:
    // Optional<User> findByUsername(String username);
}

