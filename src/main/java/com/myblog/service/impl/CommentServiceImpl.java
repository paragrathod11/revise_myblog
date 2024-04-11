package com.myblog.service.impl;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.BlogAPIException;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.mapper.CommentMapper;
import com.myblog.payload.CommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long post_id, CommentDto commentDto) {
        log.info("Create a new Comment: {}", commentDto.getBody());

        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_id)
        );

        Comment comment = mapper.toEntity(commentDto);
        comment.setPost(post);
        CommentDto createdComment = mapper.toDto(commentRepository.save(comment));

        log.info("Comment Created: '{}' by user '{}' at '{}'", createdComment.getId(), getCurrentUser(), LocalDateTime.now());
        return createdComment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        log.info("Fetching all Comments of a Post by PostID: {}", postId);

        List<Comment> allCommentsOfPost = commentRepository.findByPostId(postId);
        List<CommentDto> allComments = allCommentsOfPost.stream().map(x -> mapper.toDto(x)).collect(Collectors.toList());

        return allComments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        log.info("Fetching all Comments");
        return commentRepository.findAll().stream().map(x->mapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getPostCommentById(long postId, long commentId) {
        log.info("Fetching particular comment of a Post.");
        log.info("Fetching commentID: {} of PostID: {}", commentId, postId);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        if(comment.getPost().getId() == post.getId()){
            return mapper.toDto(comment);
        }else {
            String errorMessage = String.format("Comment not belongs to post");
            log.error(errorMessage);
            throw new BlogAPIException("Comment not belong to post.", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public CommentDto editPostCommentByCommentId(long postId, long commentId, CommentDto commentDto) {
        log.info("Updating Comment with ID: '{}' of Post with ID: '{}'", commentId, postId);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

                comment.setName(commentDto.getName());
                comment.setEmail(commentDto.getEmail());
                comment.setBody(commentDto.getBody());

        if(comment.getPost().getId() == post.getId()){
            return mapper.toDto(commentRepository.save(comment));
        }else {
            String errorMessage = String.format("Comment not belongs to Post.");
            log.error(errorMessage);
            throw new BlogAPIException("Comment not belongs to post", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void deletePostCommentByCommentId(long postId, long commentId) {
        log.info("Deleting Comment with ID: {} of Post with ID: {}", commentId, postId);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        if(comment.getPost().getId() == post.getId()){
            commentRepository.delete(comment);
        }else {
            String errorMessage = String.format("Comment not belongs to Post.");
            log.error(errorMessage);
            throw new BlogAPIException("Comment not belongs to Post", HttpStatus.BAD_REQUEST);
        }
    }


//////////////////////////////////////////////
//////////////////////////////////////////////

    // Utility method to get the current user (replace with your actual implementation)
    private String getCurrentUser() {
        // Logic to retrieve current user (e.g., from Spring Security context or session)
        return "admin"; // Example: return username or user ID
    }
}
