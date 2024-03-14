package com.example.BloggingApp.entity;

import javax.persistence.*;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 25)
    private String name;

    @Column(name = "user_email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "user_mobile", nullable = false, unique = true, length = 10)
    private long phone;

    @Column(name = "user_about", nullable = false, length = 140)
    private String about;

    @Column(name = "user_password", nullable = false, length = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable
            (
                    name = "user_roles",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles;
}
