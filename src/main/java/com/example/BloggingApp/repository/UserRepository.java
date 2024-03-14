package com.example.BloggingApp.repository;

import com.example.BloggingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNameOrEmail(String name, String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(Long phone);
}
