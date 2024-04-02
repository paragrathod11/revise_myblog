package com.myblog.api;


import com.myblog.controller.PostController;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private PostDto postDto = new PostDto();
    public PostDto getPostDto() {
        postDto.setId(1L);
        postDto.setTitle("Title 1");
        postDto.setDescription("Test description");
        postDto.setContent("Test Content.");
        return postDto;
    }
    private PostResponse postResponse = new PostResponse();
    public PostResponse getPostResponse(){
        //postResponse.setContent(Collections.singletonList(postDto));
        postResponse.setPageNo(0);
        postResponse.setPageSize(3);
        postResponse.setTotalPages(1);
        postResponse.setTotalElement(3);
        postResponse.setLast(true);
        List<PostDto> postDtoList = new ArrayList<>();
        postDtoList.add(postDto);
        postResponse.setContent(postDtoList);

        return postResponse;
    }

    @Test
    public void testSavePost(){

        PostDto postDto1 = getPostDto();

        when(postService.createPost(postDto1)).thenReturn(postDto);
        // Execute
        PostDto savedPostDto = postController.savePost(postDto);
        // Verify
//        System.out.println(postDto);
//        System.out.println(savedPostDto);
        assertEquals(postDto, savedPostDto);

    }

    @Test
    public void testGetPostById(){

        PostDto postDto1 = getPostDto();
        when(postService.getPostById(postDto1.getId())).thenReturn(postDto);

        PostDto fatchedPostDto = postController.getPostById(1L);

//        System.out.println(postDto);
//        System.out.println(fatchedPostDto);
        assertEquals(postDto, fatchedPostDto);

    }

    @Test
    public void testGetAllPost(){

        PostResponse postResponse1 = getPostResponse();
        when(postService.getAllPosts(postResponse1.getPageNo(), postResponse1.getPageSize(), "id", "asc")).thenReturn(postResponse);

        PostResponse fetchAllPosts = postController.getAllPosts(0, 3, "id", "asc");
        System.out.println(postResponse);
        System.out.println(fetchAllPosts);
        assertEquals(postResponse, fetchAllPosts);
    }

    @Test
    public void testUpdatePostById(){

        lenient().when(postService.updatePostById(anyLong(), any(PostDto.class))).thenReturn(postDto);

        PostDto updatedPostDto = postController.updatePostById(1l, postDto);
        assertEquals(postDto, updatedPostDto);
    }

    @Test
    public void testDeletePostById(){

        String result = postController.deletePostById(1L);

        assertEquals("Deleted Successfully.", result);
    }


}
