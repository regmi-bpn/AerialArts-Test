package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.BlogLike;

public interface LikeValidator {

    BlogLike findLikeById(Long likeId);
}
