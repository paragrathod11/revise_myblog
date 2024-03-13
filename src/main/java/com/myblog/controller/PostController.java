package com.myblog.controller;

import com.myblog.service.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }




}
