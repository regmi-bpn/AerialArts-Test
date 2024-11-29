package com.aerialarts.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BlogLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Blog blog;

    @Column(nullable = false, updatable = false)
    private String createdAt;

    private Boolean isDeleted = false;


}

