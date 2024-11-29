package com.aerialarts.blog.service;

import com.aerialarts.blog.service.model.CommentRequest;
import com.aerialarts.blog.service.model.CommentResponse;
import com.aerialarts.blog.service.model.Message;

import java.util.List;

public interface CommentService {

    Message addComment(CommentRequest request, Long blogId);

    Message updateComment(CommentRequest request, Long commentId);

    Message deleteComment(Long commentId);

    List<CommentResponse> getAllCommentsOfABlog(Long blogId);
}
