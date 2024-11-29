package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.Blog;

public interface BlogValidator {

    Blog findBlogById(Long blogId);

}
