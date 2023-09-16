package com.application.nothing.repository;

import com.application.nothing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    boolean existsByEmail(String email);
    // Custom queries can be added here. For example:
    // Optional<User> findByUsername(String username);
}

