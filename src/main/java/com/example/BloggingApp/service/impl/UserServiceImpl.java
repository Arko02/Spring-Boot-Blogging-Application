// UserServiceImpl.java
package com.example.BloggingApp.service.impl;

import com.example.BloggingApp.entity.User;
import com.example.BloggingApp.exception.ResourceNotFoundException;
import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.payload.UsersList;
import com.example.BloggingApp.repository.UserRepository;
import com.example.BloggingApp.service.UserService;
import com.example.BloggingApp.utill.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public UserResponseDto saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user1 = mapToEntity(userDto);
        User user = this.userRepository.save(user1);
        // Send a welcome email
        emailService.sendEmail(user.getEmail(), "Thanks For SignUp", "Enjoy");
        return mapToUserResponse(user);
    }

    @Override
    public UserResponseDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With ID: " + id));
        return mapToUserResponse(user);
    }

    @Override
    public UsersList getAllUsers() {
        return new UsersList("All User Fetch Without Pagination And Sorting", this.userRepository.findAll());
    }

    @Override
    public List<UserResponseDto> getAllUsersWithPaginationWithoutSorting(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> all = this.userRepository.findAll(pageable);
        List<User> content = all.getContent();
        return content.stream().map(user -> this.mapToUserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getAllUsersWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sortOrder = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
        Page<User> all = this.userRepository.findAll(pageable);
        List<User> content = all.getContent();
        return content.stream().map(user -> this.mapToUserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUserById(long id, UserDto userDto) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Updating Operation Failed Because User Not Found With Id: " + id));
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        User save = this.userRepository.save(user);
        return this.mapToUserResponse(save);
    }

    @Override
    public UserResponseDto updateSpecificUserById(long id, UserDto userDto) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found With Id: " + id));
        // Update only the fields that are present in the request body
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhone() != 0) {
            if (userDto.getPhone() >= 1_000_000_000L && userDto.getPhone() <= 9_999_999_999L) {
                user.setPhone(userDto.getPhone());
            } else {
                throw new IllegalArgumentException("Phone Number must be exactly 10 digits");
            }
        }
        if (userDto.getAbout() != null) {
            user.setAbout(userDto.getAbout());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        return this.mapToUserResponse(this.userRepository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Deleting Operation Failed Because User Not Found With Id: " + id));
        this.userRepository.deleteById(id);
    }

    private User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserResponseDto mapToUserResponse(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }
}
