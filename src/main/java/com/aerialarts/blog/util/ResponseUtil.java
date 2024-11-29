package com.aerialarts.blog.util;

import com.aerialarts.blog.entity.Comment;
import com.aerialarts.blog.service.model.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseUtil {

    public static CommentResponse getCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent() != null ? comment.getContent() : "No Content")
                .user(comment.getUser() != null && comment.getUser().getUsername() != null
                        ? comment.getUser().getUsername()
                        : "Anonymous")
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<CommentResponse> getCommentResponse(List<Comment> commentList) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                commentResponses.add(getCommentResponse(comment));
            }
        }
        return commentResponses;
    }
}
