package com.example.BloggingApp.service;

import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.payload.UsersList;

import java.util.List;

public interface UserService {
    UserResponseDto saveUser(UserDto userDto);

    UserResponseDto getUserById(long id);

    UsersList getAllUsers();

    List<UserResponseDto> getAllUsersWithPaginationWithoutSorting(int pageNumber, int pageSize);

    List<UserResponseDto> getAllUsersWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection);

    UserResponseDto updateUserById(long id, UserDto userDto);

    void deleteUser(long id);
}
