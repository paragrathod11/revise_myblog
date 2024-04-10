package com.myblog.controller;

import com.myblog.payload.CommentDto;
import com.myblog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Api(value = "Comment Management System", description = "Operation for the Blog post's Comments management")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    // localhost:8080/api/post/1/comments
    @PostMapping("/{post_id}/comments")
    @ApiOperation(value = "Create new Comment for particular post")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new Comment"),
            @ApiResponse(code = 400, message = "Bad Request, Validation error in the request body.")
    })
    public ResponseEntity<CommentDto> createComment(@PathVariable("post_id") long post_id, @Valid @NotNull @RequestBody CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(post_id, commentDto));
    }


    // localhost:8080/api/post/allComments
    @GetMapping("/allComments")
    @ApiOperation(value = "Retrieve all Comments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all comments of all posts."),
            @ApiResponse(code = 404, message = "Comments are not found.")
    })
    public ResponseEntity<List<CommentDto>> getAllComments(){
        return
                ResponseEntity.ok().body(commentService.getAllComments());
    }

    // localhost:8080/api/post/1/comments
    @GetMapping("/{post_id}/comments")
    @ApiOperation(value = "Retrieve all comments of particular post.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all comments of a post"),
            @ApiResponse(code = 404, message = "Comments not found for a post.")
    })
    public ResponseEntity<List<CommentDto>> getAllCommentByPostId(@PathVariable("post_id") long post_id){
        return ResponseEntity.ok().body(commentService.getAllCommentsByPostId(post_id));
    }


    // localhost:8080/api/post/1/comments/2
    @GetMapping("/{post_id}/comments/{comment_id}")
    @ApiOperation(value = "Retrieve particular comment of a particular post.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully found a particular comment."),
            @ApiResponse(code = 404, message = "Particular comment not found.")
    })
    public ResponseEntity<CommentDto> getPostCommentByCommentId(
            @PathVariable("post_id") long post_id,
            @PathVariable("comment_id") long comment_id
    ){
        return
                ResponseEntity.ok().body(commentService.getPostCommentById(post_id, comment_id));
    }

    // localhost:8080/api/post1/comments/2
    @PutMapping("/{post_id}/comments/{comment_id}")
    @ApiOperation(value = "Update particular comment of a post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated the comment of the post."),
            @ApiResponse(code = 400, message = "Bad Request. Validation error in the request body."),
            @ApiResponse(code = 404, message = "Comment not found.")
    })
    public ResponseEntity<CommentDto> editPostCommentByCommentId(
            @PathVariable("post_id") long post_id,
            @PathVariable("comment_id") long comment_id,
            @Valid @NotNull @RequestBody CommentDto commentDto
    ){
        CommentDto updatedComment = commentService.editPostCommentByCommentId(post_id, comment_id, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedComment);
    }

    @DeleteMapping("{post_id}/comments/{comment_id}")
    @ApiOperation(value = "Delete particular comment by comment id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the comment."),
            @ApiResponse(code = 404, message = "Comment not found.")
    })
    public ResponseEntity<String> deletePostCommentByCommentId(
            @PathVariable("post_id") long post_id,
            @PathVariable("comment_id") long comment_id
    ){
        commentService.deletePostCommentByCommentId(post_id, comment_id);
        return ResponseEntity.accepted().body("Comment Deleted Successfully.");
    }


}
