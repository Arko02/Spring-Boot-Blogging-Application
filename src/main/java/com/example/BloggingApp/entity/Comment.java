package com.example.BloggingApp.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String title;

    @ManyToOne
    @JoinColumn(name = "post_id") // It'll create a column in Comments Table with name post_id.
    private Post post;
}
