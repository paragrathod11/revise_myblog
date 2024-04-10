package com.myblog.api;

import com.myblog.controller.CommentController;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.payload.CommentDto;
import com.myblog.payload.PostDto;
import com.myblog.service.CommentService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

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

        long postId = 1L;

        CommentDto commentDto = getCommentDto();

        when(commentService.createComment(postId, commentDto)).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = commentController.createComment(postId, commentDto);

        System.out.println(result);
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());
        System.out.println(commentDto);
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

        when(commentService.getAllCommentsByPostId(1L)).thenReturn(allCommentsDto);

        ResponseEntity<List<CommentDto>> result = commentController.getAllCommentByPostId(1L);

//        System.out.println(result);
//        System.out.println(allCommentsDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(allCommentsDto, result.getBody());
    }

    @Test
    public void testGetPostCommentById(){

        CommentDto commentDto = getCommentDto();

        when(commentService.getPostCommentById(1L, 1L)).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = commentController.getPostCommentByCommentId(1L, 1L);

//        System.out.println(result);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(commentDto, result.getBody());

    }

    @Test
    public void testEditPostCommentByCommentId(){

        CommentDto commentDto = getCommentDto();

        when(commentService.editPostCommentByCommentId(1L, 1L, commentDto)).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = commentController.editPostCommentByCommentId(1L, 1L, commentDto);

        //System.out.println(result);
        //System.out.println(commentDto);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(commentDto, result.getBody());

    }

    @Test
    public void testDeletePostCommentById(){

        ResponseEntity<String> result = commentController.deletePostCommentByCommentId(1L, 1L);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals("Comment Deleted Successfully.", result.getBody());

    }


/////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
///////////////////////////////////////////////////////
    private final CommentDto commentDto = new CommentDto();
    public CommentDto getCommentDto(){
        commentDto.setId(1L);
        commentDto.setName("Cherry");
        commentDto.setEmail("cherry@email.com");
        commentDto.setBody("This is comment.");
        return commentDto;
    }

    private final Comment comment = new Comment();
    public Comment getComment(){
        comment.setId(1L);
        comment.setName("Cherry");
        comment.setEmail("cherry@email.com");
        comment.setBody("This is comment.");
        return comment;
    }

    private final List<CommentDto> listCommentDto = new ArrayList<>();
    public List<CommentDto> getAllCommentsDto(){
        listCommentDto.add(new CommentDto(1L, "Cherry", "cherry@email.com", "This is comment.", getPostDto()));
        listCommentDto.add(new CommentDto(2L, "Aman", "aman@email.com", "This is comment2.", getPostDto()));
        listCommentDto.add(new CommentDto(3L, "John", "john@email.com", "This is comment3.", getPostDto()));
        return listCommentDto;
    }

    private final List<Comment> listComment = new ArrayList<>();
    public List<Comment> getAllComments(){
        listComment.add(new Comment(1L, "Cherry", "cherry@email.com", "This is comment.", getPost()));
        listComment.add(new Comment(2L, "Aman", "aman@email.com", "This is comment2.", getPost()));
        listComment.add(new Comment(3L, "John", "john@email.com", "This is comment3.", getPost()));
        return listComment;
    }

    private final PostDto postDto = new PostDto();
    public PostDto getPostDto() {
        postDto.setId(1L);
        postDto.setTitle("Title 1");
        postDto.setDescription("Test description");
        postDto.setContent("Test Content.");
        return postDto;
    }

    private final Post post = new Post();
    public Post getPost() {
        post.setId(1L);
        post.setTitle("Title 1");
        post.setDescription("Test description");
        post.setContent("Test Content.");
        return post;
    }


}
