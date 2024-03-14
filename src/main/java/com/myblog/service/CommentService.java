package com.myblog.service;

import com.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long post_id, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long postId);

    List<CommentDto> getAllComments();

    CommentDto getPostCommentById(long postId, long commentId);

    CommentDto editPostCommentByCommentId(long postId, long commentId, CommentDto commentDto);

    void deletePostCommentByCommentId(long postId, long commentId);
}
