package com.aerialarts.blog.repository;

import com.aerialarts.blog.entity.Blog;
import com.aerialarts.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT comment FROM Comment comment WHERE comment.blog=:blog AND comment.isDeleted = false")
    List<Comment> findCommentsBasedOnBlog(Blog blog);

    @Query("SELECT comment FROM Comment comment WHERE comment.id=:id AND comment.isDeleted = false")
    Optional<Comment> findCommentById(Long id);
}
