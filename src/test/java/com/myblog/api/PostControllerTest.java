package com.myblog.api;


import com.myblog.controller.PostController;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;



    @Test
    public void testSavePost(){

        PostDto postDto1 = getPostDto();

        when(postService.createPost(postDto1)).thenReturn(postDto);
        // Execute
        PostDto result = postController.savePost(postDto);
        // Verify
        System.out.println(postDto);
        System.out.println(result);
        assertEquals(postDto, result);

    }

    @Test
    public void testGetPostById(){

        PostDto postDto1 = getPostDto();
        when(postService.getPostById(postDto1.getId())).thenReturn(postDto);

        PostDto fatchedPostDto = postController.getPostById(post_id);

        assertEquals(postDto, fatchedPostDto);

    }

    @Test
    public void testGetAllPost(){

        PostResponse postResponse = getPostResponse();
        when(postService.getAllPosts(postResponse.getPageNo(), postResponse.getPageSize(), "id", "asc")).thenReturn(postResponse);

        PostResponse fetchAllPosts = postController.getAllPosts(0, 3, "id", "asc");
        System.out.println(postResponse);
        System.out.println(fetchAllPosts);
        assertEquals(postResponse, fetchAllPosts);
    }

    @Test
    public void testUpdatePostById(){

        lenient().when(postService.updatePostById(anyLong(), any(PostDto.class))).thenReturn(postDto);

        PostDto updatedPostDto = postController.updatePostById(post_id, postDto);
        assertEquals(postDto, updatedPostDto);
    }

    @Test
    public void testDeletePostById(){

        String result = postController.deletePostById(post_id);

        assertEquals("Deleted Successfully.", result);
    }



/////////////////////////////////////////////////////////
///// Helper methods to create entities and DTOs ///////
///////////////////////////////////////////////////////

    static long post_id = 1L;

    private final PostDto postDto = new PostDto();
    public PostDto getPostDto() {
        postDto.setId(1L);
        postDto.setTitle("Title 1");
        postDto.setDescription("Test description");
        postDto.setContent("Test Content.");
        return postDto;
    }

    private final List<PostDto> listPostDto = new ArrayList<>();
    public List<PostDto> getAllPostDto(){
        listPostDto.add(new PostDto(1L, "Title 1", "content 1", "Description 1"));
        listPostDto.add(new PostDto(2L, "Title 2", "content 2", "Description 2"));
        listPostDto.add(new PostDto(3L, "Title 3", "content 3", "Description 3"));
        listPostDto.add(new PostDto(4L, "Title 4", "content 4", "Description 4"));
        listPostDto.add(new PostDto(5L, "Title 5", "content 5", "Description 5"));
        listPostDto.add(new PostDto(6L, "Title 6", "content 6", "Description 6"));
        return listPostDto;
    }

    private final PostResponse postResponse = new PostResponse();
    public PostResponse getPostResponse(){
        //postResponse.setContent(Collections.singletonList(postDto));
        postResponse.setContent(getAllPostDto());
        postResponse.setPageNo(0);
        postResponse.setPageSize(3);
        postResponse.setTotalPages(1);
        postResponse.setTotalElement(3);
        postResponse.setLast(true);
//        List<PostDto> postDtoList = new ArrayList<>();
//        postDtoList.add(postDto);
//        postDtoList.add(postDto);

        return postResponse;
    }


}
