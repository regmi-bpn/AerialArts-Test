package com.aerialarts.blog.service;

import com.aerialarts.blog.entity.Blog;
import com.aerialarts.blog.service.model.Message;

public interface LikeService {

    Message likeBlog(Long blogId);

    Message unlikeBlog(Long blogLikeId);

    Long likeCountOfBlog(Blog blogLike);

}
