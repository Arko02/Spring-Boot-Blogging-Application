package com.example.BloggingApp.payload;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;

    @NotEmpty(message = "Name Field cannot be empty")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "Email Field cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Phone Number Field cannot be empty")
    @Min(value = 1_000_000_000L, message = "Phone Number must be exactly 10 digits")
    @Max(value = 9_999_999_999L, message = "Phone Number must be exactly 10 digits")
    private long phone;

    @NotEmpty(message = "About Field cannot be empty")
    @Size(min = 2, max = 140, message = "About should be between 2 and 140 characters")
    private String about;

    @NotEmpty(message = "Password Field cannot be empty")
    @Size(min = 6, max = 12, message = "Password should be between 6 and 12 characters")
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).{6,12}$",
    // message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character and length of the password must be between 6 and 12 characters")
    private String password;
}
