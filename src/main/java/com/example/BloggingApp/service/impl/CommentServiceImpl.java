package com.example.BloggingApp.service.impl;

import com.example.BloggingApp.entity.Comment;
import com.example.BloggingApp.entity.Post;
import com.example.BloggingApp.exception.ResourceNotFoundException;
import com.example.BloggingApp.payload.CommentDto;
import com.example.BloggingApp.payload.CommentResponseDto;
import com.example.BloggingApp.repository.CommentRepository;
import com.example.BloggingApp.repository.PostRepository;
import com.example.BloggingApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentResponseDto saveComment(long id, CommentDto commentDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post Not Found With ID: " + id));
        Comment comment = mapDtoToEntity(commentDto);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return mapEntityToResponse(savedComment);
    }

    @Override
    public CommentResponseDto partialUpdateCommentById(long postId, long commentId, CommentDto commentDto) {
        Comment existingComment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment Not Found With ID: " + commentId));

        Post existingPost = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found With ID: " + postId));

        if (commentDto.getId() == 0)
            commentDto.setId(existingComment.getId());

        if (commentDto.getEmail() != null)
            existingComment.setEmail(commentDto.getEmail());

        if (commentDto.getTitle() != null)
            existingComment.setTitle(commentDto.getTitle());

        existingComment.setPost(existingPost);

        return mapEntityToResponse(commentRepository.save(existingComment));
    }

    @Override
    public void deleteCommentById(long id) {
        commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment Not Found With ID: " + id));
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentResponseDto> getAllTheComments() {
        List<Comment> all = this.commentRepository.findAll();
        List<CommentResponseDto> collect = all.stream().map(post -> this.mapEntityToResponse(post)).collect(Collectors.toList());
        return collect;
    }


    private Comment mapDtoToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    private CommentResponseDto mapEntityToResponse(Comment comment) {
        return modelMapper.map(comment, CommentResponseDto.class);
    }
}
