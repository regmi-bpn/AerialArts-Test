package com.aerialarts.blog.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentResponse {

    private String content;

    private String user;

    private String createdAt;

}
