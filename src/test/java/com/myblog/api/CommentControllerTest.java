package com.myblog.api;

import com.myblog.controller.CommentController;
import com.myblog.payload.CommentDto;
import com.myblog.payload.PostDto;
import com.myblog.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;


    @Test
    public void testCreateComment(){

        CommentDto commentDto = getCommentDto();

        when(commentService.createComment(post_id, commentDto)).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = commentController.createComment(post_id, commentDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(commentDto, result.getBody());
    }

    @Test
    public void testGetAllComments(){

        List<CommentDto> allCommentsDto = getAllCommentsDto();

        when(commentService.getAllComments()).thenReturn(allCommentsDto);
        
        ResponseEntity<List<CommentDto>> result = commentController.getAllComments();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(allCommentsDto, result.getBody());
    }
    
    @Test
    public void testGetAllCommentsByPostId(){

        List<CommentDto> allCommentsDto = getAllCommentsDto();

        when(commentService.getAllCommentsByPostId(post_id)).thenReturn(allCommentsDto);

        ResponseEntity<List<CommentDto>> result = commentController.getAllCommentByPostId(post_id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(allCommentsDto, result.getBody());
    }

    @Test
    public void testGetPostCommentById(){

        CommentDto commentDto = getCommentDto();

        when(commentService.getPostCommentById(post_id, comment_id)).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = commentController.getPostCommentByCommentId(post_id, comment_id);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(commentDto, result.getBody());

    }

    @Test
    public void testEditPostCommentByCommentId(){

        CommentDto commentDto = getCommentDto();

        when(commentService.editPostCommentByCommentId(post_id, comment_id, commentDto)).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = commentController.editPostCommentByCommentId(post_id, comment_id, commentDto);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(commentDto, result.getBody());

    }

    @Test
    public void testDeletePostCommentById(){

        ResponseEntity<String> result = commentController.deletePostCommentByCommentId(post_id, comment_id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals("Comment Deleted Successfully.", result.getBody());

    }


/////////////////////////////////////////////////////////
///// Helper methods to create entities and DTOs ///////
///////////////////////////////////////////////////////

    static long post_id = 1L;
    static long comment_id = 1L;

    private final CommentDto commentDto = new CommentDto();
    public CommentDto getCommentDto(){
        commentDto.setId(1L);
        commentDto.setName("Cherry");
        commentDto.setEmail("cherry@email.com");
        commentDto.setBody("This is comment.");
        return commentDto;
    }

    private final List<CommentDto> listCommentDto = new ArrayList<>();
    public List<CommentDto> getAllCommentsDto(){
        listCommentDto.add(new CommentDto(1L, "Cherry", "cherry@email.com", "This is comment.", getPostDto()));
        listCommentDto.add(new CommentDto(2L, "Aman", "aman@email.com", "This is comment2.", getPostDto()));
        listCommentDto.add(new CommentDto(3L, "John", "john@email.com", "This is comment3.", getPostDto()));
        return listCommentDto;
    }

    private final PostDto postDto = new PostDto();
    public PostDto getPostDto() {
        postDto.setId(1L);
        postDto.setTitle("Title 1");
        postDto.setDescription("Test description");
        postDto.setContent("Test Content.");
        return postDto;
    }

}
