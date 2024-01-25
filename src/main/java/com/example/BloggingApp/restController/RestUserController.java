package com.example.BloggingApp.restController;

import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.payload.UsersList;
import com.example.BloggingApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Users/API")
public class RestUserController {
    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping("/create") // POST : http://localhost:8080/Users/API/create
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(userDto));
    }

    // Retrieve a user by ID
    @GetMapping("/{id}") // GET : http://localhost:8080/Users/API/{id}
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(id));
    }

    // Retrieve all users
    @GetMapping("/all") // GET : http://localhost:8080/Users/API/all
    public ResponseEntity<UsersList> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
    }

    // Retrieve users with pagination (without sorting)
    @GetMapping("/pagination") // GET : http://localhost:8080/Users/API/pagination?pageNumber=?&pageSize=?
    public List<UserResponseDto> findAllUsersWithPaginationWithoutSorting(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        return this.userService.getAllUsersWithPaginationWithoutSorting(pageNumber, pageSize);
    }

    // Retrieve users with pagination and sorting
    @GetMapping("/paginationAndSorting")
    // GET : http://localhost:8080/Users/API/paginationAndSorting?pageNumber=?&pageSize=?&sortBy=?&sortDirection=?
    public List<UserResponseDto> findAllUsersWithPaginationAndSorting(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
                                                                      @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                                      @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        return this.userService.getAllUsersWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortDirection);
    }

    // Update a user by ID
    @PutMapping("/{id}") // PUT : http://localhost:8080/Users/API/{id}
    public ResponseEntity<?> updateUserById(@PathVariable long id, @Valid @RequestBody UserDto userDto) {
        // Set the ID of the userDto
        userDto.setId(id);
        UserResponseDto userResponseDto = this.userService.updateUserById(id, userDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}") // DELETE : http://localhost:8080/Users/API/{id}
    public ResponseEntity<String> deleteUserById(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted with Id: " + id);
    }
}
