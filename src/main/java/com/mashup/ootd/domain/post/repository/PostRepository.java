package com.mashup.ootd.domain.post.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mashup.ootd.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("SELECT p FROM Post p ORDER BY p.id DESC")
	List<Post> findAllDesc();

	List<Post> findTop20ByOrderByIdDesc();
	
	@Query("SELECT DISTINCT ps.post FROM PostStyle ps"
			+ " where ps.style.id in (:styleIds)"
			+ " and ps.post.weather = :weather"
			+ " and ps.post.temperature >= :minTemp"
			+ " and ps.post.temperature <= :maxTemp"
			+ " and ps.post.id < :lastPostId")
	Page<Post> findAllByFilter(@Param("styleIds") Set<Long> styleIds, @Param("weather") String weather,
			@Param("minTemp") int minTemp, @Param("maxTemp") int maxTemp, @Param("lastPostId") Long lastPostId, Pageable pageable);

}
