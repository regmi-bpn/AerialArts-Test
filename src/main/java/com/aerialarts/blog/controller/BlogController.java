package com.aerialarts.blog.controller;

import com.aerialarts.blog.service.BlogService;
import com.aerialarts.blog.service.model.BlogRequest;
import com.aerialarts.blog.service.model.BlogResponse;
import com.aerialarts.blog.service.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/v1/blog")
    public Message createBlog(@RequestBody BlogRequest request) {
        return blogService.createBlog(request);
    }

    @PutMapping("/v1/blog")
    public Message updateBlogBlog(@RequestBody BlogRequest request, @Param("id") Long blogId) {
        return blogService.updateBlogBlog(request, blogId);
    }

    @GetMapping("/v1/blog")
    public List<BlogResponse> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/v1/blog/author")
    public List<BlogResponse> getAllBlogsByAuthor(@Param("authorId") Long authorId) {
        return blogService.getAllBlogsByAuthor(authorId);
    }
}
