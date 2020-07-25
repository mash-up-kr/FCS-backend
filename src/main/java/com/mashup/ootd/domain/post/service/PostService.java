package com.mashup.ootd.domain.post.service;

import com.mashup.ootd.domain.post.dto.PostGetResponse;
import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final FileUploader fileUploader;

	private static final String DIRECTORY_NAME = "post";

	public void create(PostCreateRequest dto) {
		String url = fileUploader.upload(dto.getUploadFile(), DIRECTORY_NAME);

		Post post = dto.toEntity(url);

		postRepository.save(post);
	}

	public List<PostGetResponse> findAllDesc(){
		return postRepository.findAllDesc().stream()
				.map(PostGetResponse::new)
				.collect(Collectors.toList());
	}

}
