package com.example.BloggingApp.restController;

import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.payload.UsersList;
import com.example.BloggingApp.repository.UserRepository;
import com.example.BloggingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class RestUserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    // Create a new user
//    @PostMapping("/create") // http://localhost:8080/api/user/create
//    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
//
//        if (userRepository.existsByEmail(userDto.getEmail()))
//            return new ResponseEntity<>("This Email is already Exist", HttpStatus.BAD_REQUEST);
//
//
//        if (userRepository.existsByPhone(userDto.getPhone()))
//            return new ResponseEntity<>("This Phone Number is already Exist", HttpStatus.BAD_REQUEST);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(userDto));
//    }

    // Retrieve a User by ID
    @GetMapping("/{id}") // http://localhost:8080/api/user/1
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(id));
    }

    // Retrieve All Users
    @GetMapping("/all") // http://localhost:8080/api/user/all
    public ResponseEntity<UsersList> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
    }

    // Retrieve ALL Users with pagination Without Sorting
    // Query Parameter -> @RequestParam
    @GetMapping("/pagination") // http://localhost:8080/api/user/pagination?pageNumber=?&pageSize=?
    public List<UserResponseDto> findAllUsersWithPaginationWithoutSorting(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        return this.userService.getAllUsersWithPaginationWithoutSorting(pageNumber, pageSize);
    }

    // Retrieve Users with Pagination and Sorting
    // Query Parameter -> @RequestParam
    @GetMapping("/paginationAndSorting")
    // http://localhost:8080/api/user/paginationAndSorting?pageNumber=?&pageSize=?&sortBy=?&sortDirection=?
    public List<UserResponseDto> findAllUsersWithPaginationAndSorting(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
                                                                      @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                                      @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        return this.userService.getAllUsersWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortDirection);
    }

    // Update User by ID
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT
    @PutMapping("/{id}") // http://localhost:8080/api/user/1
    public ResponseEntity<?> updateUserById(@PathVariable long id, @Valid @RequestBody UserDto userDto) {
        // Set the ID of the userDto
        userDto.setId(id);
        UserResponseDto userResponseDto = this.userService.updateUserById(id, userDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // Partial Update User by ID
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT
    @PatchMapping("/{id}") //  http://localhost:8080/api/user/1
    public ResponseEntity<?> partialUpdateUserById(@PathVariable long id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateSpecificUserById(id, userDto));
    }

    // Delete a User by ID
    // Path Variable / Path Parameter -> @PathVariable -> IDEMPOTENT -> NOT UPSERT
    @DeleteMapping("/{id}") // http://localhost:8080/api/user/1
    public ResponseEntity<String> deleteUserById(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted with Id: " + id);
    }
}
