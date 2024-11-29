package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.Comment;

public interface CommentValidator {

    Comment findCommentById(Long id);
}
