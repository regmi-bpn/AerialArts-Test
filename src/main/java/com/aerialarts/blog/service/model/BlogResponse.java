package com.aerialarts.blog.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class BlogResponse {

    private String title;

    private String content;

    private String author;

    private List<CommentResponse> comments;

    private String blogImage;

    private String createdAt;

    private Long likeCount;

}
