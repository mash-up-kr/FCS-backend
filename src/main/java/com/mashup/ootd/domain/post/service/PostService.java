package com.mashup.ootd.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.post.dto.PostGetResponse;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.repository.PostRepository;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.service.StyleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final FileUploader fileUploader;
	private final StyleService styleService;

	private static final String DIRECTORY_NAME = "post";

	@Transactional
	public PostCreateResponse create(PostCreateRequest dto) {
		String url = fileUploader.upload(dto.getUploadFile(), DIRECTORY_NAME);

		Post post = dto.toEntity(url);
		
		List<Style> styles = styleService.listByStyleIds(dto.getStyleIds());

		post.addStyles(styles);

		postRepository.save(post);

		return new PostCreateResponse(post.getId(), post.getPhotoUrl());
	}

	public List<PostGetResponse> listTop20(){
		return postRepository.findTop20ByOrderByIdDesc().stream()
				.map(PostGetResponse::of)
				.collect(Collectors.toList());
	}

}
