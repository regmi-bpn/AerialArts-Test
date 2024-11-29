package com.aerialarts.blog.repository;

import com.aerialarts.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT blog FROM Blog blog WHERE blog.author.id=:authorId AND blog.isDeleted = false")
    List<Blog> findBlogsByAuthor(Long authorId);

    @Query("SELECT blog FROM Blog blog WHERE blog.isDeleted = false")
    List<Blog> findAllUndeletedBlogs();
}
