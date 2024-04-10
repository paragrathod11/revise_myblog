package com.myblog.service;

import com.myblog.entity.Post;
import com.myblog.mapper.PostMapper;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.impl.PostServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testCreatePost() {
        // Given
        PostDto postDto = getPostDto();
        Post post = getPost();
        when(postMapper.toEntity(postDto)).thenReturn(post);
        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postMapper.toDto(post)).thenReturn(postDto);

        // When
        PostDto result = postService.createPost(postDto);

        // Then
        assertNotNull(result);
        assertEquals(postDto, result);
        System.out.println(postDto);
        System.out.println(result);
    }

    @Test
    public void testGetPostById(){

        Post post = getPost();
        PostDto postDto = getPostDto();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postDto);

        PostDto retrievePostById = postService.getPostById(post_id);

        assertEquals(postDto, retrievePostById);
    }

    @Test
    public void testGetAllPosts(){

    // Given
        int pageNo = 0;
        int pageSize = 3;
        String sortBy = "id";
        String sortDir = "asc";

        List<Post> allPosts = getAllPost();
        Page<Post> pagePosts = new PageImpl<>(allPosts);
        PostResponse expectedResponse = getPostResponse();

        when(postRepository.findAll(any(Pageable.class))).thenReturn(pagePosts);
        when(postMapper.toDto(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getDescription());
        });

        System.out.println("Expected Response ="+expectedResponse);

    // When
        PostResponse result = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        System.out.println("result="+result);

    // Then
        assertNotNull(result);
        //assertEquals(expectedResponse, result);
        assertEquals(expectedResponse.getContent().size(), result.getContent().size());
        assertEquals(expectedResponse.getPageNo(), result.getPageNo());
        assertEquals(expectedResponse.getPageSize(), result.getPageSize());
        assertEquals(expectedResponse.getTotalElement(), result.getTotalElement());
        assertEquals(expectedResponse.getTotalPages(), result.getTotalPages());
        assertEquals(expectedResponse.isLast(), result.isLast());

    }

    @Test
    public void testUpdatePostById(){

        Post post = getPost();
        PostDto postDto = getPostDto();

        when(postRepository.findById(post_id)).thenReturn(Optional.of(post));
        when(postMapper.toEntity(postDto)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDto);

        PostDto result = postService.updatePostById(post_id, postDto);

        assertNotNull(result);
        assertEquals(postDto, result);
    }

    @Test
    public void testDeletePostById(){

        Post post = new Post();

        when(postRepository.findById(post_id)).thenReturn(Optional.of(post));

        postService.deletePostById(post_id);

        verify(postRepository).delete(post);
    }




/////////////////////////////////////////////////////////
///// Helper methods to create entities and DTOs ///////
///////////////////////////////////////////////////////

    static long post_id = 1L;

    private final PostDto postDto = new PostDto();
    public PostDto getPostDto(){
        postDto.setId(1L);
        postDto.setTitle("Title 1");
        postDto.setDescription("Test description");
        postDto.setContent("Test Content.");
        return postDto;
    }

    private final Post post = new Post();
    public Post getPost(){
        post.setId(1L);
        post.setTitle("Title 1");
        post.setDescription("Test description");
        post.setContent("Test Content.");
        return post;
    }

    private final List<PostDto> listPostDto = new ArrayList<>();
    public List<PostDto> getAllPostDto(){
        listPostDto.add(new PostDto(1L, "Title 1", "content 1", "Description 1"));
        listPostDto.add(new PostDto(2L, "Title 2", "content 2", "Description 2"));
        listPostDto.add(new PostDto(3L, "Title 3", "content 3", "Description 3"));
        listPostDto.add(new PostDto(1L, "Title 4", "content 4", "Description 4"));
        listPostDto.add(new PostDto(2L, "Title 5", "content 5", "Description 5"));
        listPostDto.add(new PostDto(3L, "Title 6", "content 6", "Description 6"));
        return listPostDto;
    }
    private final List<Post> listPost = new ArrayList<>();
    public List<Post> getAllPost(){
        listPost.add(new Post(1L, "Title 1", "content 1", "Description 1"));
        listPost.add(new Post(2L, "Title 2", "content 2", "Description 2"));
        listPost.add(new Post(3L, "Title 3", "content 3", "Description 3"));
        listPost.add(new Post(1L, "Title 4", "content 4", "Description 4"));
        listPost.add(new Post(2L, "Title 5", "content 5", "Description 5"));
        listPost.add(new Post(3L, "Title 6", "content 6", "Description 6"));
        return listPost;
    }


    private final PostResponse postResponse = new PostResponse();
    public  PostResponse getPostResponse(){
        //postResponse.setContent(Collections.singletonList(postDto));
        postResponse.setContent(getAllPostDto());
        postResponse.setPageNo(0);
        postResponse.setPageSize(6);
        postResponse.setTotalPages(1);
        postResponse.setTotalElement(6);
        postResponse.setLast(true);

        return postResponse;
    }

}

