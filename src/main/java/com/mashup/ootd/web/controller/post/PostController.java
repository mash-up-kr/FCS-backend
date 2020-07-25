package com.mashup.ootd.web.controller.post;

import com.mashup.ootd.domain.SearchParam;
import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.dto.PostGetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mashup.ootd.domain.post.service.PostService;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

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

	@GetMapping
	public ResponseEntity<OotdResponse<List<PostGetResponse>>> getList() {
		List<PostGetResponse> response = postService.findAllDesc();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<List<PostGetResponse>>builder()
						.code(HttpStatus.OK.value())
						.msg("피드 정보 반환")
						.data(response)
						.build());
	}


}
