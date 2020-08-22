package com.mashup.ootd.domain.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mashup.ootd.domain.post.entity.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	@Query("SELECT p FROM Post p ORDER BY p.id DESC")
	List<Post> findAllDesc();

	List<Post> findTop20ByOrderByIdDesc();

	Page<Post> findAllBYStyleIds(Pageable pageable);
}
