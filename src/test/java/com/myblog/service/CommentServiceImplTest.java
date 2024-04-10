package com.myblog.service;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.mapper.CommentMapper;
import com.myblog.payload.CommentDto;
import com.myblog.payload.PostDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.impl.CommentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void testCreateComment(){

        Post post = getPost();
        Comment comment = getComment();
        CommentDto commentDto = getCommentDto();

        when(postRepository.findById(post_id)).thenReturn(Optional.of(post));
        when(commentMapper.toEntity(commentDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.createComment(post_id, commentDto);

        assertNotNull(result);
        assertEquals(commentDto, result);

    }

    @Test
    public void testGetAllCommentsByPostId(){

        List<Comment> allComments = getAllComments();
        List<CommentDto> allCommentsDto = getAllCommentsDto();

        when(commentRepository.findByPostId(post_id)).thenReturn(allComments);
        when(commentMapper.toDto(any(Comment.class))).thenAnswer(
                x -> {
                    Comment comment = x.getArgument(0);
                    return new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody(), getPostDto());
                }
        );

        List<CommentDto> result = commentService.getAllCommentsByPostId(1L);

        assertNotNull(result);
        assertEquals(allCommentsDto, result);
    }

    @Test
    public void testGetAllComments(){

        List<CommentDto> allCommentsDto = getAllCommentsDto();
        List<Comment> allComments = getAllComments();

        when(commentRepository.findAll()).thenReturn(allComments);
        when(commentMapper.toDto(any(Comment.class))).thenAnswer(
                x-> {
                    Comment comment = x.getArgument(0);
                    return new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody(), getPostDto());
                }
        );

        List<CommentDto> result = commentService.getAllComments();

        assertNotNull(result);
        assertEquals(allCommentsDto, result);
    }

    @Test
    public void testGetPostCommentById(){

        Post post = getPost();
        Comment comment = getComment();
        CommentDto commentDto = getCommentDto();

        when(postRepository.findById(post_id)).thenReturn(Optional.of(post));
        when(commentRepository.findById(comment_id)).thenReturn(Optional.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.getPostCommentById(post_id, comment_id);

        assertNotNull(result);
        assertEquals(commentDto, result);

    }

    @Test
    public void testEditPostCommentByCommentId(){

        Post post = getPost();
        Comment comment = getComment();
        CommentDto commentDto = getCommentDto();

        when(postRepository.findById(post_id)).thenReturn(Optional.of(post));
        when(commentRepository.findById(comment_id)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.editPostCommentByCommentId(post_id, comment_id, commentDto);

        assertNotNull(result);
        assertEquals(commentDto, result);
    }

    @Test
    public void testDeletePostCommentByCommentId(){

        Post post = getPost();
        Comment comment = getComment();

        when(postRepository.findById(post_id)).thenReturn(Optional.of(post));
        when(commentRepository.findById(comment_id)).thenReturn(Optional.of(comment));

        commentService.deletePostCommentByCommentId(post_id, comment_id);

        verify(commentRepository).delete(comment);
    }




/////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
///////////////////////////////////////////////////////

    static long post_id = 1L;
    static long comment_id = 1L;
    private final CommentDto commentDto = new CommentDto();
    public CommentDto getCommentDto(){
        commentDto.setId(1L);
        commentDto.setName("Cherry");
        commentDto.setEmail("cherry@email.com");
        commentDto.setBody("This is comment.");
        commentDto.setPost(getPostDto());
        return commentDto;
    }

    private final Comment comment = new Comment();
    public Comment getComment(){
        comment.setId(1L);
        comment.setName("Cherry");
        comment.setEmail("cherry@email.com");
        comment.setBody("This is comment.");
        comment.setPost(getPost());
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
