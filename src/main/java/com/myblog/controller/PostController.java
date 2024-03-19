package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Api(value = "Post Management system", description = "Operation for the Blog post management")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a Blog Post
    // localhost:8080/api/post
    @PostMapping
    @ApiOperation(value = "Create a new Blog Post.")
    @ApiResponses(value ={
        @ApiResponse(code = 201, message = "Successfully created a new Blog Post."),
        @ApiResponse(code = 400, message = "Bad Request. Validation error in the request body.")
    })
    public PostDto savePost(@Valid @RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }


    // Get/Find Post data by post id
    // localhost:8080/api/post/1
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Blog post by 'post id'.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the blog post."),
            @ApiResponse(code = 404, message = "Blog post not found.")
    })
    public PostDto getPostById(@PathVariable long id){
        return postService.getPostById(id);
    }

    @GetMapping
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update a blog post by its id.", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the blog post"),
            @ApiResponse(code = 404, message = "Blog post not found")
    })
    public PostDto updatePostById(@PathVariable long id, @Valid @RequestBody PostDto postDto){
        return postService.updatePostById(id, postDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a blog post by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
            @ApiResponse(code = 404, message = "Blog post not found")
    })
    public String deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return "Deleted Successfully.";
    }

}
