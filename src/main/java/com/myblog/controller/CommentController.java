package com.myblog.controller;

import com.myblog.payload.CommentDto;
import com.myblog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    // localhost:8080/api/post/1/comments
    @PostMapping("/{post_id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("post_id") long post_id, @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok().body(commentService.createComment(post_id, commentDto));
    }


    // localhost:8080/api/post/allComments
    @GetMapping("/allComments")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        return
                ResponseEntity.ok().body(commentService.getAllComments());
    }

    // localhost:8080/api/post/1/comments
    @GetMapping("/{post_id}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentByPostId(@PathVariable("post_id") long post_id){
        return ResponseEntity.ok().body(commentService.getAllCommentsByPostId(post_id));
    }


    // localhost:8080/api/post/1/comments/2
    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> getPostCommentByCommentId(
            @PathVariable("post_id") long post_id,
            @PathVariable("comment_id") long comment_id
    ){
        return
                ResponseEntity.ok().body(commentService.getPostCommentById(post_id, comment_id));
    }

    // localhost:8080/api/post1/comments/2
    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> editPostCommentByCommentId(
            @PathVariable("post_id") long post_id,
            @PathVariable("comment_id") long comment_id,
            @Valid @RequestBody CommentDto commentDto
    ){
        CommentDto updatedComment = commentService.editPostCommentByCommentId(post_id, comment_id, commentDto);
        return ResponseEntity.ok().body(updatedComment);
    }

    @DeleteMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deletePostCommentByCommentId(
            @PathVariable("post_id") long post_id,
            @PathVariable("comment_id") long comment_id
    ){
        commentService.deletePostCommentByCommentId(post_id, comment_id);
        return ResponseEntity.accepted().body("Comment Deleted Successfully.");
    }





}
