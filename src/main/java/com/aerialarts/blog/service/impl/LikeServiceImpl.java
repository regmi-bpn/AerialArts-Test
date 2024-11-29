package com.aerialarts.blog.service.impl;

import com.aerialarts.blog.entity.Blog;
import com.aerialarts.blog.entity.BlogLike;
import com.aerialarts.blog.entity.User;
import com.aerialarts.blog.repository.LikeRepository;
import com.aerialarts.blog.service.LikeService;
import com.aerialarts.blog.service.model.Message;
import com.aerialarts.blog.service.validators.BlogValidator;
import com.aerialarts.blog.service.validators.LikeValidator;
import com.aerialarts.blog.service.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    // I have created like service and model like this in case we create profile of each user which can be accessed through like too.

    private final LikeRepository likeRepository;

    private final BlogValidator blogValidator;

    private final UserValidator userValidator;

    private final LikeValidator likeValidator;


    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, BlogValidator blogValidator, UserValidator userValidator, LikeValidator likeValidator) {
        this.likeRepository = likeRepository;
        this.blogValidator = blogValidator;
        this.userValidator = userValidator;
        this.likeValidator = likeValidator;
    }

    @Override
    public Message likeBlog(Long blogId) {
        BlogLike blogLike = new BlogLike();
        User user = userValidator.getUser();
        if (!checkIfTheBlogIsLikedOrNot(blogId, user)) {
            blogLike.setBlog(blogValidator.findBlogById(blogId));
            blogLike.setUser(user);
            blogLike.setCreatedAt(String.valueOf(Date.from(Instant.now())));
            likeRepository.save(blogLike);
        }
        return Message.builder().message("Blog liked successfully.").build();
    }

    @Override
    public Message unlikeBlog(Long likeId) {
        BlogLike like = likeValidator.findLikeById(likeId);
        like.setIsDeleted(true);
        likeRepository.save(like);
        return Message.builder().message("Blog Unliked successfully.").build();
    }

    @Override
    public Long likeCountOfBlog(Blog blog) {
        return likeRepository.findTotalNoOfLikeOfBlog(blog);
    }

    private boolean checkIfTheBlogIsLikedOrNot(Long blogId, User user) {
        Optional<BlogLike> like = likeRepository.findIfBlogIsLikedOrNot(blogId, user);
        if (like.isPresent()) {
            BlogLike blogLike = like.get();
            blogLike.setIsDeleted(true);
            return true;
        }
        return false;
    }

}
