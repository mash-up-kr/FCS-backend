package com.mashup.ootd.web.controller.post;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.post.dto.PostGetResponse;
import com.mashup.ootd.domain.post.service.PostService;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<OotdResponse<PostCreateResponse>> create(User user, PostCreateRequest dto) {
		PostCreateResponse response = postService.create(user, dto); 

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(OotdResponse.<PostCreateResponse>builder()
						.code(HttpStatus.CREATED.value())
						.msg("업로드 성공")
						.data(response)
						.build());
	}

	@GetMapping
	public ResponseEntity<OotdResponse<List<PostGetResponse>>> list() {
		List<PostGetResponse> response = postService.listTop20();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<List<PostGetResponse>>builder()
						.code(HttpStatus.OK.value())
						.msg("피드 정보 반환")
						.data(response)
						.build());
	}


}
