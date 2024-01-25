package com.example.BloggingApp.service;

import com.example.BloggingApp.payload.PostDto;
import com.example.BloggingApp.payload.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostDto postDto);

    PostResponseDto getPostById(long id);

    List<PostResponseDto> getAllPostsWithoutPaginationAndSorting();

    List<PostResponseDto> getAllPostsWithPaginationWithoutSorting(int pageNumber, int pageSize);

    List<PostResponseDto> getAllPostsWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PostResponseDto updatePostById(long id, PostDto postDto);
}
