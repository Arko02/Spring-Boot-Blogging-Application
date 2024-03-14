package com.example.BloggingApp.restController;

import com.example.BloggingApp.payload.PostDto;
import com.example.BloggingApp.payload.PostResponseDto;
import com.example.BloggingApp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class RestPostController {

    private final PostService postService;

    public RestPostController(PostService postService) {
        this.postService = postService;
    }

    // Create New Post.
    @PostMapping("/create") // http://localhost:8080/api/post/create
    public ResponseEntity<PostResponseDto> createNewPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(postDto));
    }

    // Retrieve a post by ID.
    @GetMapping("/{id}") // http://localhost:8080/api/post/1
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostById(id));
    }

    // Retrieve all posts without pagination and sorting.
    @GetMapping("/all") // http://localhost:8080/api/post/all
    public ResponseEntity<List<PostResponseDto>> findAllPostsWithoutPaginationAndSorting() {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPostsWithoutPaginationAndSorting());
    }

    // Retrieve all posts with pagination without sorting.
    @GetMapping("/page") // http://localhost:8080/api/post/page?pageNumber=&pageSize=
    public ResponseEntity<List<PostResponseDto>> findAllPostsWithPaginationWithoutSorting(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPostsWithPaginationWithoutSorting(pageNumber, pageSize));
    }

    // Retrieve all posts with pagination and sorting.
    @GetMapping("/sorted") // http://localhost:8080/api/post/sorted?pageNumber=&pageSize=&sortBy={sortBy}&sortDirection=
    public ResponseEntity<List<PostResponseDto>> findAllPostsWithPaginationAndSorting(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy, @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPostsWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortDirection));
    }

    // Update a post by ID.
    @PutMapping("/{id}") // http://localhost:8080/api/post/1
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable long id, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePostById(id, postDto));
    }

    // Delete a post by ID.
    @DeleteMapping("/delete/{id}") // http://localhost:8080/api/post/delete/1
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        this.postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post Deleted With ID: " + id);
    }
}
