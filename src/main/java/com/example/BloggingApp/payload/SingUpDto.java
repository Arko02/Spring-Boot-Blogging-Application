package com.example.BloggingApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingUpDto {
    private long id;
    private String name;

    private String email;

    private long phone;

    private String about;

    private String password;

}
