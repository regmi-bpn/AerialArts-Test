package com.aerialarts.blog.controller;

import com.aerialarts.blog.service.CommentService;
import com.aerialarts.blog.service.model.CommentRequest;
import com.aerialarts.blog.service.model.CommentResponse;
import com.aerialarts.blog.service.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/v1/comment")
    public Message addComment(@RequestBody CommentRequest request, @Param("blogId") Long blogId) {
        return commentService.addComment(request, blogId);
    }

    @PutMapping("/v1/comment")
    public Message updateComment(@RequestBody CommentRequest request, @Param("commentId") Long commentId) {
        return commentService.updateComment(request, commentId);
    }

    @DeleteMapping("/v1/comment")
    public Message deleteComment(@Param("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/v1/comment")
    public List<CommentResponse> getAllCommentsOfABlog(@Param("blogId") Long blogId) {
        return commentService.getAllCommentsOfABlog(blogId);
    }

}
