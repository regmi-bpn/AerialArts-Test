package com.aerialarts.blog.repository;

import com.aerialarts.blog.entity.Blog;
import com.aerialarts.blog.entity.BlogLike;
import com.aerialarts.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<BlogLike, Long> {

    @Query("SELECT COUNT(like) FROM BlogLike like WHERE like.blog=:blog AND like.isDeleted = false")
    Long findTotalNoOfLikeOfBlog(Blog blog);

    @Query("SELECT like FROM BlogLike like WHERE like.blog.id=:blogId AND like.user=:user AND like.isDeleted = true")
    Optional<BlogLike> findIfBlogIsLikedOrNot(Long blogId, User user);
}
