package com.aerialarts.blog.service;

import com.aerialarts.blog.service.model.BlogRequest;
import com.aerialarts.blog.service.model.BlogResponse;
import com.aerialarts.blog.service.model.Message;

import java.util.List;

public interface BlogService {

    Message createBlog(BlogRequest request);

    Message updateBlogBlog(BlogRequest request, Long blogId);

    List<BlogResponse> getAllBlogs();

    List<BlogResponse> getAllBlogsByAuthor(Long authorId);
}
