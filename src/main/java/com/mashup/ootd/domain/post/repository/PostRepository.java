package com.mashup.ootd.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mashup.ootd.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("SELECT p FROM Post p ORDER BY p.id DESC")
	List<Post> findAllDesc();

	List<Post> findTop20ByOrderByIdDesc();

}
