package com.example.BloggingApp.repository;

import com.example.BloggingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
