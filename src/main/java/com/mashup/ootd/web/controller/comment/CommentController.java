package com.mashup.ootd.web.controller.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.comment.dto.CommentCreateRequest;
import com.mashup.ootd.domain.comment.dto.CommentCreateResponse;
import com.mashup.ootd.domain.comment.dto.CommentResponse;
import com.mashup.ootd.domain.comment.service.CommentService;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/{post-id}")
	public ResponseEntity<OotdResponse<CommentCreateResponse>> create(User user,
			@PathVariable(name = "post-id") Long postId, @RequestBody CommentCreateRequest dto) {
		
		CommentCreateResponse response = commentService.save(user, postId, dto);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(OotdResponse.<CommentCreateResponse>builder()
						.code(HttpStatus.CREATED.value())
						.msg("댓글 등록 성공")
						.data(response)
						.build());
	}
	
	@GetMapping("/{post-id}")
	public ResponseEntity<OotdResponse<List<CommentResponse>>> get(@PathVariable(name = "post-id") Long postId) {
	
		List<CommentResponse> response = commentService.list(postId);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<List<CommentResponse>>builder()
						.code(HttpStatus.OK.value())
						.msg("댓글 정보 반환")
						.data(response)
						.build());
	}

}
