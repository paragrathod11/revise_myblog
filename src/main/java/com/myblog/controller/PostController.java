package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
