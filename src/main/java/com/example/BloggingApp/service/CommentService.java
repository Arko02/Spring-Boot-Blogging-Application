package com.example.BloggingApp.service;

import com.example.BloggingApp.payload.CommentDto;
import com.example.BloggingApp.payload.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto saveComment(long postId, CommentDto commentDto);

    CommentResponseDto partialUpdateCommentById(long commentId, long postId, CommentDto commentDto);

    void deleteCommentById(long id);

    List<CommentResponseDto> getAllTheComments();
}
