package com.aerialarts.blog.controller;

import com.aerialarts.blog.service.LikeService;
import com.aerialarts.blog.service.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/v1/like")
    public Message likeBlog(@Param("blogId") Long blogId) {
        return likeService.likeBlog(blogId);
    }

    @DeleteMapping("/v1/like")
    public Message unlikeBlog(@Param("likeId") Long likeId) {
        return likeService.unlikeBlog(likeId);
    }
}
