package com.example.BloggingApp.restController;

import com.example.BloggingApp.payload.LoginDto;
import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.repository.UserRepository;
import com.example.BloggingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /**
     * Endpoint for user sign-in.
     *
     * @param loginDto Data transfer object containing user credentials.
     * @return ResponseEntity indicating the success or failure of the sign-in process.
     */
    @PostMapping("/signin") // http://localhost:8080/api/auth/signin
    public ResponseEntity<String> signIn(@RequestBody LoginDto loginDto) {
        // Creating authentication token using provided credentials
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getNameOrEmail(), loginDto.getPassword());

        // Authenticating the user
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Setting the authentication object in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Responding with success message
        return ResponseEntity.status(HttpStatus.OK).body("User signed in successfully.");
    }

    // Create a new user
    @PostMapping("/create") // http://localhost:8080/api/auth/create
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail()))
            return new ResponseEntity<>("This Email is already Exist", HttpStatus.BAD_REQUEST);


        if (userRepository.existsByPhone(userDto.getPhone()))
            return new ResponseEntity<>("This Phone Number is already Exist", HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(userDto));
    }

}
