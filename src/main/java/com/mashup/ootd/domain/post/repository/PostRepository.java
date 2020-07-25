package com.mashup.ootd.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mashup.ootd.domain.post.entity.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

}
