package com.mashup.ootd.domain.post.service;

import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.repository.PostRepository;
import com.mashup.ootd.domain.style.service.PostStyleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final FileUploader fileUploader;
	private final PostStyleService postStyleService;

	private static final String DIRECTORY_NAME = "post";

	public void create(PostCreateRequest dto) {
		String url = fileUploader.upload(dto.getUploadFile(), DIRECTORY_NAME);

		Post post = dto.toEntity(url);

		postRepository.save(post);
		postStyleService.save(post, dto.getStyleIds());
	}

}
