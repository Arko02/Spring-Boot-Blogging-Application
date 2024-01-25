package com.example.BloggingApp.service.impl;

import com.example.BloggingApp.entity.User;
import com.example.BloggingApp.exception.ResourceNotFoundException;
import com.example.BloggingApp.payload.UserDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.payload.UsersList;
import com.example.BloggingApp.repository.UserRepository;
import com.example.BloggingApp.service.UserService;
import com.example.BloggingApp.utill.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public UserResponseDto saveUser(UserDto userDto) {
        User user = this.userRepository.save(mapToEntity(userDto));
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
        Sort shortOrder = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, shortOrder);
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

    // Delete -> DELETE : http://localhost:8080/Users/API/Value
    @Override
    public void deleteUser(long id) {
        this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Deleting Operation Failed Because User Not Found With Id: " + id));
        this.userRepository.deleteById(id);
    }


    private User mapToEntity(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPhone(),
                userDto.getAbout(),
                userDto.getPassword()
        );
    }

    private UserResponseDto mapToUserResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAbout(),
                user.getPassword()
        );
    }
}
