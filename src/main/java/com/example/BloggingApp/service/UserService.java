package com.example.BloggingApp.service;

import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.payload.UsersList;

import java.util.List;

public interface UserService {
    // Save a new user
    // Endpoint: POST : http://localhost:8080/Users/API/create
    // NOT IDEMPOTENT
    UserResponseDto saveUser(UserDto userDto);

    // Retrieve a user by ID
    // Endpoint: GET : http://localhost:8080/Users/API/{id}
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT
    UserResponseDto getUserById(long id);

    // Retrieve all users
    // Endpoint: GET : http://localhost:8080/Users/API/all
    UsersList getAllUsers();

    // Retrieve users with pagination (without sorting)
    // Endpoint: GET : http://localhost:8080/Users/API/pagination?pageNumber=?&pageSize=?
    List<UserResponseDto> getAllUsersWithPaginationWithoutSorting(int pageNumber, int pageSize);

    // Retrieve users with pagination and sorting
    // Query Parameter -> @RequestParam
    // Endpoint: GET : http://localhost:8080/Users/API/paginationAndSorting?pageNumber=?&pageSize=?&sortBy=?&sortDirection=?
    List<UserResponseDto> getAllUsersWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection);

    // Update a user by ID
    // Endpoint: PUT : http://localhost:8080/Users/API/{id}
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT
    UserResponseDto updateUserById(long id, UserDto userDto);

    // Partial update of a user by ID
    // Endpoint: PATCH : http://localhost:8080/Users/API/{id}
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT
    UserResponseDto updateSpecificUserById(long id, UserDto userDto);

    // Delete a user by ID
    // Endpoint: DELETE : http://localhost:8080/Users/API/{id}
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT -> NOT UPSERT
    void deleteUser(long id);
}
