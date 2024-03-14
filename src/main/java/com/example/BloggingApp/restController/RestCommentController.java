package com.example.BloggingApp.restController;

import com.example.BloggingApp.payload.CommentDto;
import com.example.BloggingApp.payload.CommentResponseDto;
import com.example.BloggingApp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class RestCommentController {

    private final CommentService commentService;

    public RestCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create a new comment for a specific post.
    @PostMapping("/create/{postId}") // http://localhost:8080/api/comment/create/1
    public ResponseEntity<CommentResponseDto> createNewComment(@PathVariable long postId, @Valid @RequestBody CommentDto commentDto) {
        CommentResponseDto createdComment = commentService.saveComment(postId, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/all") // http://localhost:8080/api/comment/all
    public List<CommentResponseDto> getAllTheComments() {
        List<CommentResponseDto> allTheComments = this.commentService.getAllTheComments();
        return allTheComments;
    }

    //  Partially Update an existing comment by its ID for a Specific Post.
    @PatchMapping("/update/{commentId}/{postId}") // http://localhost:8080/api/comment/update/1/1
    public ResponseEntity<CommentResponseDto> partialUpdateCommentById(@PathVariable long commentId, @PathVariable long postId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.partialUpdateCommentById(commentId, postId, commentDto));
    }

    // delete a comment by its ID.
    @DeleteMapping("/{id}") // http://localhost:8080/api/comment/1
    public ResponseEntity<String> deleteCommentById(@PathVariable long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Comment Delete Successful With ID: " + id);
    }
}
