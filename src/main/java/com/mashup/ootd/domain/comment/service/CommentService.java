package com.mashup.ootd.domain.comment.service;

import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.comment.dto.CommentCreateRequest;
import com.mashup.ootd.domain.comment.dto.CommentCreateResponse;
import com.mashup.ootd.domain.comment.entity.Comment;
import com.mashup.ootd.domain.comment.repository.CommentRepository;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.service.PostService;
import com.mashup.ootd.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final PostService postService;
	private final CommentRepository commentRepository;

	public CommentCreateResponse save(User user, Long postId, CommentCreateRequest dto) {
		Post post = postService.get(postId);

		Comment comment = commentRepository.save(new Comment(dto.getMessage(), user, post));
		
		return new CommentCreateResponse(comment.getId(), comment.getMessage());
	}

}
