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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_id)
        );

        Comment comment = mapper.toEntity(commentDto);
        comment.setPost(post);

        return mapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {

        List<Comment> allCommentsOfPost = commentRepository.findByPostId(postId);
        List<CommentDto> allComments = allCommentsOfPost.stream().map(x -> mapper.toDto(x)).collect(Collectors.toList());

        return allComments;
    }

    @Override
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(x->mapper.toDto(x)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getPostCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        if(comment.getPost().getId() == post.getId()){
            return mapper.toDto(comment);
        }else {
            throw new BlogAPIException("Comment not belong to post.", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public CommentDto editPostCommentByCommentId(long postId, long commentId, CommentDto commentDto) {

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
            throw new BlogAPIException("Comment not belongs to post", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void deletePostCommentByCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        if(comment.getPost().getId() == post.getId()){
            commentRepository.delete(comment);
        }else {
            throw new BlogAPIException("Comment not belongs to Post", HttpStatus.BAD_REQUEST);
        }
    }
}
