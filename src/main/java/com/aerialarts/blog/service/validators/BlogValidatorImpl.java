package com.aerialarts.blog.service.validators;

import com.aerialarts.blog.entity.Blog;
import com.aerialarts.blog.exception.BadRequestException;
import com.aerialarts.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogValidatorImpl implements BlogValidator {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogValidatorImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog findBlogById(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(() -> new BadRequestException("No Blog can be found from the provided detail."));
    }
}
