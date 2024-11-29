package com.aerialarts.blog.service.impl;

import com.aerialarts.blog.entity.Blog;
import com.aerialarts.blog.repository.BlogRepository;
import com.aerialarts.blog.service.BlogService;
import com.aerialarts.blog.service.LikeService;
import com.aerialarts.blog.service.model.BlogRequest;
import com.aerialarts.blog.service.model.BlogResponse;
import com.aerialarts.blog.service.model.CommentResponse;
import com.aerialarts.blog.service.model.Message;
import com.aerialarts.blog.service.validators.BlogValidator;
import com.aerialarts.blog.service.validators.UserValidator;
import com.aerialarts.blog.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

    private final UserValidator userValidator;

    private final BlogRepository blogRepository;

    private final BlogValidator blogValidator;

    private final LikeService likeService;

    @Autowired
    public BlogServiceImpl(UserValidator userValidator, BlogRepository blogRepository, BlogValidator blogValidator, LikeService likeService) {
        this.userValidator = userValidator;
        this.blogRepository = blogRepository;
        this.blogValidator = blogValidator;
        this.likeService = likeService;
    }

    @Override
    public Message createBlog(BlogRequest request) {
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setBlogImage(request.getBlogImage());
        blog.setCreatedAt(String.valueOf(Date.from(Instant.now())));
        blog.setAuthor(userValidator.getUser());
        blogRepository.save(blog);
        return Message.builder().message("The blog has been created successfully.").build();
    }

    @Override
    public Message updateBlogBlog(BlogRequest request, Long blogId) {
        Blog blog = blogValidator.findBlogById(blogId);
        userValidator.findBlogUpdateAccess(blog.getAuthor());
        if (request.getTitle() != null) blog.setTitle(request.getTitle());
        if (request.getContent() != null) blog.setContent(request.getContent());
        if (request.getBlogImage() != null) blog.setBlogImage(request.getBlogImage());
        blog.setUpdatedAt(String.valueOf(Date.from(Instant.now())));
        blogRepository.save(blog);
        return Message.builder().message("The blog has been updated successfully.").build();
    }

    @Override
    public List<BlogResponse> getAllBlogs() {
        return getBlogResponseList(blogRepository.findAllUndeletedBlogs());
    }

    @Override
    public List<BlogResponse> getAllBlogsByAuthor(Long authorId) {
        return getBlogResponseList(blogRepository.findBlogsByAuthor(authorId));
    }

    private List<BlogResponse> getBlogResponseList(List<Blog> blogList) {
        List<BlogResponse> blogResponses = new ArrayList<>();

        if (blogList == null) {
            log.info("Blog list is null, returning empty response list.");
            return blogResponses;
        }

        for (Blog blog : blogList) {
            if (blog != null) {
                try {
                    blogResponses.add(getBlogResponse(blog));
                } catch (Exception e) {
                    log.info("Error processing blog: {}", e.getMessage());
                }
            } else {
                log.info("Encountered a null blog in the list, skipping.");
            }
        }

        return blogResponses;
    }


    private BlogResponse getBlogResponse(Blog blog) {
        String author = blog.getAuthor() != null ? blog.getAuthor().getUsername() : "Unknown";

        List<CommentResponse> commentResponse = ResponseUtil.getCommentResponse(
                blog.getComments() == null ?
                        Collections.emptyList() :
                        blog.getComments()
                                .stream()
                                .filter(e -> !e.getIsDeleted())
                                .collect(Collectors.toList())
        );

        Long likeCount = 0L;
        try {
            likeCount = likeService.likeCountOfBlog(blog);
        } catch (Exception e) {
            log.info("Error calculating like count: {}", e.getMessage());
        }

        return BlogResponse.builder()
                .title(blog.getTitle() != null ? blog.getTitle() : "Untitled")
                .content(blog.getContent() != null ? blog.getContent() : "No Content")
                .author(author)
                .comments(commentResponse)
                .blogImage(blog.getBlogImage())
                .createdAt(blog.getCreatedAt())
                .likeCount(likeCount)
                .build();
    }


}
