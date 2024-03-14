package com.example.BloggingApp.service.impl;

import com.example.BloggingApp.entity.Post;
import com.example.BloggingApp.exception.ResourceNotFoundException;
import com.example.BloggingApp.payload.PostDto;
import com.example.BloggingApp.payload.PostResponseDto;
import com.example.BloggingApp.repository.PostRepository;
import com.example.BloggingApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostResponseDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    @Override
    public PostResponseDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapToPostResponse(post);
    }

    @Override
    public List<PostResponseDto> getAllPostsWithoutPaginationAndSorting() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getAllPostsWithPaginationWithoutSorting(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> posts = page.getContent();
        return posts.stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getAllPostsWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> posts = page.getContent();
        return posts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    @Override
    public PostResponseDto updatePostById(long id, PostDto postDto) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        // Update the existing post entity
        existingPost.setTitle(postDto.getTitle());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setContent(postDto.getContent());

        // Save the updated post entity
        Post updatedPost = postRepository.save(existingPost);
        return mapToPostResponse(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    private Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    private PostResponseDto mapToPostResponse(Post post) {
        return modelMapper.map(post, PostResponseDto.class);
    }
}
