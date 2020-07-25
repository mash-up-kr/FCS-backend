package com.mashup.ootd.web.controller.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.service.PostService;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<OotdResponse<Void>> create(PostCreateRequest dto) {
		postService.create(dto); 

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(OotdResponse.<Void>builder()
						.code(HttpStatus.CREATED.value())
						.msg("업로드 성공")
						.build());
	}

}
