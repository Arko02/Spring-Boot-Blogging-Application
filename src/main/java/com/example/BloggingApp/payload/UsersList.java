package com.example.BloggingApp.payload;

import com.example.BloggingApp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersList {
    private String message;
    private List<User> users;
}
