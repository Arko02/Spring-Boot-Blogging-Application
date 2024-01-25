package com.example.BloggingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name", nullable = false, length = 25)
    private String name;

    @Column(name = "user_email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "user_mobile", nullable = false, unique = true, length = 10)
    private long phone;

    @Column(name = "user_about", nullable = false, length = 140)
    private String about;

    @Column(name = "user_password", nullable = false, length = 12)
    private String password;
}