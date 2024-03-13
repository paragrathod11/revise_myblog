package com.myblog.service.impl;

import com.myblog.mapper.PostMapper;
import com.myblog.payload.PostDto;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private PostMapper mapper;
    public PostServiceImpl(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        return mapper.toDto(postRepository.save(mapper.toEntity(postDto)));
    }



}
