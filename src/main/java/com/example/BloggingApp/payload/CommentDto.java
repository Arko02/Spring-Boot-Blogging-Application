package com.example.BloggingApp.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;

    @NotEmpty(message = "Email Field cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Title Field cannot be empty")
    @Size(min = 2, max = 2000, message = "Title should be between 2 and 2000 characters")
    private String title;
}
