package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a Blog Post
    // localhost:8080/api/post
    @PostMapping
    public PostDto savePost(@Valid @RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }


    // Get/Find Post data by post id
    // localhost:8080/api/post/1
    @GetMapping("/{id}")
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
    public PostDto updatePostById(@PathVariable long id, @Valid @RequestBody PostDto postDto){
        return postService.updatePostById(id, postDto);
    }

    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return "Deleted Successfully.";
    }

}
