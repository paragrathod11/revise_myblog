package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.mapper.PostMapper;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
//@Slf4j
public class PostServiceImpl implements PostService {

    private static final Logger log = LogManager.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
    private final PostMapper mapper;
    public PostServiceImpl(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        log.info("Create a new Post: {}", postDto.getTitle());

        PostDto createdPost = mapper.toDto(postRepository.save(mapper.toEntity(postDto)));

        log.info("Post Created: '{}' by user '{}' at '{}'", createdPost.getId(), getCurrentUser(), LocalDateTime.now());
        return createdPost;

    }

    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(long id) {
        log.info("Fetching post by ID: {}", id);

        Post getPostById = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", id)
        );

        return mapper.toDto(getPostById);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Fetching all Posts with pagination - Page: {}, Size: {}, Sort by: {}, Sort Direction: {}", pageNo, pageSize, sortBy, sortDir);

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

        log.debug("Fetched {} posts", allPostsDto.size());
        return postResponse;
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        log.info("Updating post with ID: {}", id);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        Post post1 = mapper.toEntity(postDto);
        post1.setId(post.getId());
        PostDto updatedPostDto = mapper.toDto(postRepository.save(post1));

        log.info("Post Updated: {} by user {} at {}", updatedPostDto.getId(), getCurrentUser(), LocalDateTime.now());
        return updatedPostDto;
    }

    @Override
    public void deletePostById(long id) {
        log.info("Deleting post with ID: {}", id);

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", id)
        );

        postRepository.delete(post);
        log.info("Post Deleted: {} by user {} at {}", id, getCurrentUser(), LocalDateTime.now());
    }

//////////////////////////////////////////////
//////////////////////////////////////////////

    // Utility method to get the current user (replace with your actual implementation)
    private String getCurrentUser() {
        // Logic to retrieve current user (e.g., from Spring Security context or session)
        return "admin"; // Example: return username or user ID
    }

}
