package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.mapper.PostMapper;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;
    public PostServiceImpl(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        return mapper.toDto(postRepository.save(mapper.toEntity(postDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(long id) {
        Post getPostById = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", id)
        );

        return mapper.toDto(getPostById);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> allPosts = pagePosts.getContent();

        System.out.println("ALL POSTS : "+allPosts);

        //List<PostDto> allPostsDto = allPosts.stream().map(x -> mapper.toDto(x)).collect(Collectors.toList());
        List<PostDto> allPostsDto = allPosts.stream().map(mapper::toDto).collect(Collectors.toList());


        PostResponse postResponse = new PostResponse();
                     postResponse.setContent(allPostsDto);
                     postResponse.setPageNo(pagePosts.getNumber());
                     postResponse.setPageSize(pagePosts.getSize());
                     postResponse.setTotalPages(pagePosts.getTotalPages());
                     postResponse.setTotalElement(pagePosts.getTotalElements());
                     postResponse.setLast(pagePosts.isLast());


        return postResponse;
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        Post post1 = mapper.toEntity(postDto);
        post1.setId(post.getId());
        return mapper.toDto(postRepository.save(post1));

    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", id)
        );

        postRepository.delete(post);
    }

}
