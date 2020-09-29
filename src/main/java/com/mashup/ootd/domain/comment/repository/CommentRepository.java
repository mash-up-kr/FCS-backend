package com.mashup.ootd.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mashup.ootd.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
