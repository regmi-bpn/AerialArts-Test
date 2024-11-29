package com.aerialarts.blog.service.impl;

import com.aerialarts.blog.entity.Comment;
import com.aerialarts.blog.repository.CommentRepository;
import com.aerialarts.blog.service.CommentService;
import com.aerialarts.blog.service.model.CommentRequest;
import com.aerialarts.blog.service.model.CommentResponse;
import com.aerialarts.blog.service.model.Message;
import com.aerialarts.blog.service.validators.BlogValidator;
import com.aerialarts.blog.service.validators.CommentValidator;
import com.aerialarts.blog.service.validators.UserValidator;
import com.aerialarts.blog.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentValidator commentValidator;

    private final BlogValidator blogValidator;

    private final UserValidator userValidator;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentValidator commentValidator, BlogValidator blogValidator, UserValidator userValidator) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
        this.blogValidator = blogValidator;
        this.userValidator = userValidator;
    }

    @Override
    public Message addComment(CommentRequest request, Long blogId) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setBlog(blogValidator.findBlogById(blogId));
        comment.setUser(userValidator.getUser());
        comment.setCreatedAt(String.valueOf(Date.from(Instant.now())));
        commentRepository.save(comment);
        return Message.builder().message("Comment posted successfully.").build();
    }

    @Override
    public Message updateComment(CommentRequest request, Long commentId) {
        Comment comment = commentValidator.findCommentById(commentId);
        if (request.getContent() != null) comment.setContent(request.getContent());
        commentRepository.save(comment);
        return Message.builder().message("Comment updated successfully.").build();
    }

    @Override
    public Message deleteComment(Long commentId) {
        Comment comment = commentValidator.findCommentById(commentId);
        comment.setIsDeleted(true);
        commentRepository.save(comment);
        return Message.builder().message("Comment deleted successfully.").build();
    }

    @Override
    public List<CommentResponse> getAllCommentsOfABlog(Long blogId) {
        ArrayList<CommentResponse> arrayList = new ArrayList<>();
        List<Comment> comments = commentRepository.findCommentsBasedOnBlog(blogValidator.findBlogById(blogId));
        for (Comment comment : comments) {
            arrayList.add(ResponseUtil.getCommentResponse(comment));
        }
        return arrayList;
    }
}
