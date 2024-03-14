package com.example.BloggingApp.payload;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    @NotEmpty(message = "Title Field cannot be empty")
    @Size(min = 2, max = 20, message = "Title should be between 2 and 200 characters")
    private String title;

    @NotEmpty(message = "Description Field cannot be empty")
    @Size(min = 2, max = 500, message = "Description should be between 2 and 500 characters")
    private String description;

    @NotEmpty(message = "Content Field cannot be empty")
    @Size(min = 2, max = 5000, message = "Content should be between 2 and 5000 characters")
    private String content;
}
