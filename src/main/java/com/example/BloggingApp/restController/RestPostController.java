package com.example.BloggingApp.restController;

import com.example.BloggingApp.payload.PostDto;
import com.example.BloggingApp.payload.PostResponseDto;
import com.example.BloggingApp.payload.UserResponseDto;
import com.example.BloggingApp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Post/Api")
public class RestPostController {
    private final PostService postService;

    public RestPostController(PostService postService) {
        this.postService = postService;
    }

    // Create a new post
    @PostMapping // POST : http://localhost:8080/Post/Api
    public ResponseEntity<PostResponseDto> createNewPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(postDto));
    }

    // Retrieve a post by ID
    @GetMapping("/{id}") // GET : http://localhost:8080/Post/Api/{id}
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostById(id));
    }

    // Retrieve all posts without pagination and sorting
    @GetMapping("/all") // GET : http://localhost:8080/Post/Api/all
    public List<PostResponseDto> findAllPostsWithoutPaginationAndSorting() {
        return this.postService.getAllPostsWithoutPaginationAndSorting();
    }

    // Retrieve all posts with pagination without sorting
    @GetMapping("/page") // GET : http://localhost:8080/Post/Api/page?pageNumber=?&pageSize=?
    public List<PostResponseDto> findAllPostsWithPaginationWithoutSorting(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        return this.postService.getAllPostsWithPaginationWithoutSorting(pageNumber, pageSize);
    }

    // Retrieve all posts with pagination and sorting
    @GetMapping("/sorted")
    // GET : http://localhost:8080/Post/Api/sorted?pageNumber=?&pageSize=?&sortBy=?&sortDirection=?
    public List<PostResponseDto> findAllPostsWithPaginationAndSorting(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
                                                                      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                                      @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return this.postService.getAllPostsWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortDirection);
    }

    // Update a post by ID
    @PutMapping("/{id}") // PUT : http://localhost:8080/Post/Api/{id}
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable long id, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePostById(id, postDto));
    }
}
