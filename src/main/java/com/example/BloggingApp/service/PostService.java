package com.example.BloggingApp.service;

import com.example.BloggingApp.payload.PostDto;
import com.example.BloggingApp.payload.PostResponseDto;

import java.util.List;

public interface PostService {

    // Create a new post
    PostResponseDto createPost(PostDto postDto);

    // Retrieve a post by ID
    PostResponseDto getPostById(long id);

    // Retrieve all posts without pagination and sorting
    List<PostResponseDto> getAllPostsWithoutPaginationAndSorting();

    // Retrieve all posts with pagination without sorting
    List<PostResponseDto> getAllPostsWithPaginationWithoutSorting(int pageNumber, int pageSize);

    // Retrieve all posts with pagination and sorting
    List<PostResponseDto> getAllPostsWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection);

    // Update a post by ID
    PostResponseDto updatePostById(long id, PostDto postDto);

    void deletePostById(long id);
}
