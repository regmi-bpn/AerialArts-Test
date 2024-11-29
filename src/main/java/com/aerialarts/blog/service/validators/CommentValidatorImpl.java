package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.Comment;
import com.aerialarts.blog.exception.BadRequestException;
import com.aerialarts.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentValidatorImpl implements CommentValidator {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentValidatorImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findCommentById(Long id) {
        return commentRepository.findCommentById(id).orElseThrow(() -> new BadRequestException("Comment cannot be found based on the provided detail."));
    }
}
