package com.example.BloggingApp.service.impl;

import com.example.BloggingApp.entity.Post;
import com.example.BloggingApp.exception.ResourceNotFoundException;
import com.example.BloggingApp.payload.PostDto;
import com.example.BloggingApp.payload.PostResponseDto;
import com.example.BloggingApp.repository.PostRepository;
import com.example.BloggingApp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponseDto createPost(PostDto postDto) {
        return this.mapToPostResponse(this.postRepository.save(this.mapToEntity(postDto)));
    }

    @Override
    public PostResponseDto getPostById(long id) {
        return this.mapToPostResponse(this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post Not Found With Id: " + id)));
    }

    @Override
    public List<PostResponseDto> getAllPostsWithoutPaginationAndSorting() {
        List<Post> all = this.postRepository.findAll();
        return all.stream().map(post -> this.mapToPostResponse(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getAllPostsWithPaginationWithoutSorting(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> all = this.postRepository.findAll(pageable);
        List<Post> content = all.getContent();
        return content.stream().map(post -> this.mapToPostResponse(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getAllPostsWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sortOrders = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOrders);
        Page<Post> all = this.postRepository.findAll(pageable);
        List<Post> content = all.getContent();
        return content.stream().map(post -> this.mapToPostResponse(post)).collect(Collectors.toList());
    }

    @Override
    public PostResponseDto updatePostById(long id, PostDto postDto) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Update Post Operation Is Failed Because Post Not Found with Id: " + id));
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return this.mapToPostResponse(this.postRepository.save(post));
    }

    Post mapToEntity(PostDto postDto) {
        return new Post(postDto.getId(), postDto.getTitle(), postDto.getDescription(), postDto.getContent());
    }

    PostResponseDto mapToPostResponse(Post post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent());
    }
}
